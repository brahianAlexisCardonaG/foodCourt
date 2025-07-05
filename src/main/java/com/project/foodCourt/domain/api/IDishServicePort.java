package com.project.foodCourt.domain.api;

import com.project.foodCourt.domain.model.DishModel;

public interface IDishServicePort {
    DishModel createDish(DishModel dishModel);
    DishModel updateDish(DishModel dishModel);
}
