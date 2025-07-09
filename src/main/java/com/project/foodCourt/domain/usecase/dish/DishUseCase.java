package com.project.foodCourt.domain.usecase.dish;

import com.project.foodCourt.domain.api.IDishServicePort;
import com.project.foodCourt.domain.model.CategoryModel;
import com.project.foodCourt.domain.model.DishModel;
import com.project.foodCourt.domain.model.RestaurantModel;
import com.project.foodCourt.domain.model.modelbasic.CategoryBasicModel;
import com.project.foodCourt.domain.model.modelbasic.RestaurantBasicModel;
import com.project.foodCourt.domain.model.modelbasic.mapper.ICategoryBasicModelMapper;
import com.project.foodCourt.domain.model.modelbasic.mapper.IRestaurantBasicModelMapper;
import com.project.foodCourt.domain.spi.ICategoryPersistencePort;
import com.project.foodCourt.domain.spi.IDishPersistencePort;
import com.project.foodCourt.domain.spi.IRestaurantPersistencePort;
import com.project.foodCourt.utils.ErrorCatalog;
import com.project.foodCourt.utils.GenericValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@RequiredArgsConstructor
public class DishUseCase implements IDishServicePort {

    private final IRestaurantPersistencePort iRestaurantPersistencePort;
    private final IDishPersistencePort iDishPersistencePort;
    private final GenericValidation genericValidation;
    private final IRestaurantBasicModelMapper iRestaurantBasicEntityMapper;
    private final ICategoryPersistencePort iCategoryPersistencePort;
    private final ICategoryBasicModelMapper iCategoryBasicModelMapper;

    @Override
    public DishModel createDish(DishModel dishModel) {
        Optional<RestaurantModel> restaurantModel = iRestaurantPersistencePort
                .getRestaurantById(dishModel.getRestaurant().getId());
        genericValidation.validateCondition(restaurantModel.isEmpty(), ErrorCatalog.RESTAURANT_NOT_FOUND);
        Optional<DishModel> dishModelFound = iDishPersistencePort
                .findByName(dishModel.getName());
        genericValidation.validateCondition(dishModelFound.isPresent(), ErrorCatalog.DISH_ALREADY_EXISTS);
        Optional<CategoryModel> categoryModel = iCategoryPersistencePort
                .getCategoryById(dishModel.getCategory().getId());
        genericValidation.validateCondition(categoryModel.isEmpty(), ErrorCatalog.CATEGORY_NOT_FOUND);

        RestaurantBasicModel restaurantBasicModel = iRestaurantBasicEntityMapper
                .toRestaurantBasicModel(restaurantModel.get());
        dishModel.setRestaurant(restaurantBasicModel);
        CategoryBasicModel categoryBasicModel = iCategoryBasicModelMapper
                .toCategoryBasicModel(categoryModel.get());
        dishModel.setCategory(categoryBasicModel);
        dishModel.setActive(true);
        return iDishPersistencePort.save(dishModel);
    }

    @Override
    public DishModel updateDish(DishModel dishModel) {
        Optional<DishModel> existingDish = iDishPersistencePort
                .findById(dishModel.getId());
        genericValidation.validateCondition(existingDish.isEmpty(), ErrorCatalog.DISH_NOT_FOUND);
        DishModel dishToUpdate = existingDish.get();
        dishToUpdate.setPrice(dishModel.getPrice());
        dishToUpdate.setDescription(dishModel.getDescription());

        return iDishPersistencePort.save(dishToUpdate);
    }

    @Override
    public DishModel disableEnableDish(DishModel dishModel) {
        Optional<DishModel> existingDish = iDishPersistencePort
                .findById(dishModel.getId());
        genericValidation.validateCondition(existingDish.isEmpty(), ErrorCatalog.DISH_NOT_FOUND);
        DishModel dishToUpdate = existingDish.get();
        Optional<RestaurantModel> getRestaurantModel = iRestaurantPersistencePort
                .getRestaurantById(dishToUpdate.getRestaurant().getId());
        genericValidation.validateCondition(getRestaurantModel.isEmpty(), ErrorCatalog.RESTAURANT_NOT_FOUND);
        RestaurantModel restaurantModel = getRestaurantModel.get();
        genericValidation.validateCondition(!dishModel.getRestaurant().getOwnerId()
                .equals(restaurantModel.getOwnerId()), ErrorCatalog.RESTAURANT_NOT_OWNER);
        dishToUpdate.setActive(dishModel.getActive());
        return iDishPersistencePort.save(dishToUpdate);
    }

    @Override
    public Page<DishModel> getAllDishesByRestaurantId(Pageable pageable, Long restaurantId, Long categoryId) {
        Optional<RestaurantModel> restaurantModel = iRestaurantPersistencePort.getRestaurantById(restaurantId);
        genericValidation.validateCondition(restaurantModel.isEmpty(), ErrorCatalog.RESTAURANT_NOT_FOUND);
        
        if (categoryId != null) {
            Optional<CategoryModel> categoryModel = iCategoryPersistencePort.getCategoryById(categoryId);
            genericValidation.validateCondition(categoryModel.isEmpty(), ErrorCatalog.CATEGORY_NOT_FOUND);
        }
        
        return iDishPersistencePort.findDishesByRestaurantId(pageable, restaurantId, categoryId);
    }
}