package com.project.foodCourt.application.mapper.dish;

import com.project.foodCourt.application.dto.request.DishRequestDto;
import com.project.foodCourt.application.dto.request.DishUpdateRequestDto;
import com.project.foodCourt.domain.model.DishModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IDishRequestMapper {
    @Mapping(source = "restaurantId", target = "restaurant.id")
    DishModel toDishModel(DishRequestDto dishRequestDto);

    DishModel toDishModelUpdate(DishUpdateRequestDto dishUpdateRequestDto);
}
