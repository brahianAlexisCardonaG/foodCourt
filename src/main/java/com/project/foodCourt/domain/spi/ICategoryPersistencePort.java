package com.project.foodCourt.domain.spi;

import com.project.foodCourt.domain.model.CategoryModel;

import java.util.Optional;

public interface ICategoryPersistencePort {
    Optional<CategoryModel> getCategoryById(Long id);
}
