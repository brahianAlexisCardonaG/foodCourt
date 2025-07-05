package com.project.foodCourt.application.mapper.dish;

import com.project.foodCourt.application.dto.response.DishResponseDto;
import com.project.foodCourt.domain.model.DishModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IDishResponseMapper {
    DishResponseDto toDishResponseDto(DishModel dishModel);
}
