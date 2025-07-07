package com.project.foodCourt.domain.api;

import com.project.foodCourt.domain.model.DishModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IDishServicePort {
    DishModel createDish(DishModel dishModel);
    DishModel updateDish(DishModel dishModel);
    DishModel disableEnableDish(DishModel dishModel);
    Page<DishModel> getAllDishesByRestaurantId(Pageable pageable, Long restaurantId, Long categoryId);
}
