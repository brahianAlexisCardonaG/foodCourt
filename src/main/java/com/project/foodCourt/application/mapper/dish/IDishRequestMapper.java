package com.project.foodCourt.application.mapper.dish;

import com.project.foodCourt.application.dto.request.dish.DishEnableDisableRequestDto;
import com.project.foodCourt.application.dto.request.dish.DishRequestDto;
import com.project.foodCourt.application.dto.request.dish.DishUpdateRequestDto;
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

    @Mapping(source = "userId", target = "restaurant.ownerId")
    DishModel toDishModelEnableDisable(DishEnableDisableRequestDto dishEnableDisableRequestDto);
}
