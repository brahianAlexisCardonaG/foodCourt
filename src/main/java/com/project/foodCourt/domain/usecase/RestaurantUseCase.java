package com.project.foodCourt.domain.usecase;

import com.project.foodCourt.domain.api.IRestaurantServicePort;
import com.project.foodCourt.domain.model.RestaurantModel;
import com.project.foodCourt.domain.model.feignclient.UserRoleResponse;
import com.project.foodCourt.domain.spi.IRestaurantPersistencePort;
import com.project.foodCourt.domain.spi.IUserWebClientPort;
import com.project.foodCourt.utils.ErrorCatalog;
import com.project.foodCourt.utils.GenericValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@RequiredArgsConstructor
public class RestaurantUseCase implements IRestaurantServicePort {

    private final IRestaurantPersistencePort iRestaurantPersistencePort;
    private final IUserWebClientPort iUserWebClientPort;
    private final GenericValidation genericValidation;

    @Override
    public RestaurantModel createRestaurant(RestaurantModel restaurantModel) {
        UserRoleResponse userRoleResponse = iUserWebClientPort
                .getUserById(restaurantModel.getOwnerId());
        genericValidation.validateCondition(userRoleResponse == null, ErrorCatalog.USER_NOT_FOUND);
        genericValidation.validateCondition(!userRoleResponse
                .getRole().getName().equals("OWNER"), ErrorCatalog.USER_NOT_OWNER);
        genericValidation.validateCondition(iRestaurantPersistencePort
                .getRestaurantByName(restaurantModel.getName()).isPresent(), ErrorCatalog.RESTAURANT_ALREADY_EXISTS);
        genericValidation.validateCondition(iRestaurantPersistencePort
                .getRestaurantByNit(restaurantModel.getNit()).isPresent(), ErrorCatalog.RESTAURANT_ALREADY_EXISTS);
        return iRestaurantPersistencePort.save(restaurantModel);
    }

    @Override
    public Page<RestaurantModel> getAllRestaurants(Pageable pageable) {
        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by("name").ascending()
        );
        return iRestaurantPersistencePort.getAllRestaurants(sortedPageable);
    }
}
