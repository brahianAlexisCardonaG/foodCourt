package com.project.foodCourt.infrastructure.out.jpa.adapter;

import com.project.foodCourt.domain.model.CategoryModel;
import com.project.foodCourt.domain.spi.ICategoryPersistencePort;
import com.project.foodCourt.infrastructure.out.jpa.entity.CategoryEntity;
import com.project.foodCourt.infrastructure.out.jpa.mapper.ICategoryEntityMapper;
import com.project.foodCourt.infrastructure.out.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CategoryJpaAdapter implements ICategoryPersistencePort {
    private final ICategoryEntityMapper categoryEntityMapper;
    private final ICategoryRepository categoryRepository;

    @Override
    public Optional<CategoryModel> getCategoryById(Long id) {
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(id);
        return categoryEntity.map(categoryEntityMapper::toCategoryModel);
    }
}
