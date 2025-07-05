package com.project.foodCourt.domain.model.modelbasic.mapper;

import com.project.foodCourt.domain.model.DishModel;
import com.project.foodCourt.domain.model.modelbasic.DishBasicModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IDishBasicModelMapper {
    DishBasicModel toDishBasicModel(DishModel dishModel);
    DishModel toDishModel(DishBasicModel dishBasicModel);
}
