package com.project.foodCourt.infrastructure.out.jpa.mapper;

import com.project.foodCourt.domain.model.CategoryModel;
import com.project.foodCourt.infrastructure.out.jpa.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICategoryEntityMapper {
    CategoryModel toCategoryModel(CategoryEntity categoryEntity);
}
