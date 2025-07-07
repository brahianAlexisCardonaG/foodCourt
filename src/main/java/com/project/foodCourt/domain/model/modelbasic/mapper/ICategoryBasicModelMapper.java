package com.project.foodCourt.domain.model.modelbasic.mapper;

import com.project.foodCourt.domain.model.CategoryModel;
import com.project.foodCourt.domain.model.modelbasic.CategoryBasicModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICategoryBasicModelMapper {
    CategoryBasicModel toCategoryBasicModel(CategoryModel categoryModel);
    CategoryModel toCategoryModel(CategoryBasicModel categoryBasicModel);
}
