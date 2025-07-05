package com.project.foodCourt.domain.spi;

import com.project.foodCourt.domain.model.DishModel;

public interface IDishPersistencePort {
    DishModel save(DishModel dishModel);
}
