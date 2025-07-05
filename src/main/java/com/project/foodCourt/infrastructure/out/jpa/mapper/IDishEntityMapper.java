package com.project.foodCourt.infrastructure.out.jpa.mapper;

import com.project.foodCourt.domain.model.DishModel;
import com.project.foodCourt.infrastructure.out.jpa.entity.DishEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IDishEntityMapper {
    DishModel toDishModel(DishEntity dishEntity);
    DishEntity toDishEntity(DishModel dishModel);
}
