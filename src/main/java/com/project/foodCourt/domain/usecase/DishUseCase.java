package com.project.foodCourt.domain.usecase;

import com.project.foodCourt.domain.api.IDishServicePort;
import com.project.foodCourt.domain.model.DishModel;
import com.project.foodCourt.domain.model.RestaurantModel;
import com.project.foodCourt.domain.model.modelbasic.RestaurantBasicModel;
import com.project.foodCourt.domain.model.modelbasic.mapper.IRestaurantBasicModelMapper;
import com.project.foodCourt.domain.spi.IDishPersistencePort;
import com.project.foodCourt.domain.spi.IRestaurantPersistencePort;
import com.project.foodCourt.utils.ErrorCatalog;
import com.project.foodCourt.utils.GenericValidation;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class DishUseCase implements IDishServicePort {

    private final IRestaurantPersistencePort iRestaurantPersistencePort;
    private final IDishPersistencePort iDishPersistencePort;
    private final GenericValidation genericValidation;
    private final IRestaurantBasicModelMapper restaurantBasicEntityMapper;

    @Override
    public DishModel createDish(DishModel dishModel) {
        Optional<RestaurantModel> restaurantModel = iRestaurantPersistencePort
                .getRestaurantById(dishModel.getRestaurant().getId());
        genericValidation.validateCondition(restaurantModel.isEmpty(), ErrorCatalog.RESTAURANT_NOT_FOUND);
        RestaurantBasicModel restaurantBasicModel = restaurantBasicEntityMapper.toRestaurantBasicModel(restaurantModel.get());
        dishModel.setRestaurant(restaurantBasicModel);
        dishModel.setActive(true);
        return iDishPersistencePort.save(dishModel);
    }
}