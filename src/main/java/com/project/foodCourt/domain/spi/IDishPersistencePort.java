package com.project.foodCourt.domain.spi;

import com.project.foodCourt.domain.model.DishModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IDishPersistencePort {
    DishModel save(DishModel dishModel);
    Optional<DishModel> findByName(String name);
    Optional<DishModel> findById(Long id);
    Page<DishModel> findDishesByRestaurantId(Pageable pageable, Long restaurantId, Long categoryId);
}
