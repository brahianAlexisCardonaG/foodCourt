package com.project.foodCourt.application.mapper.user;

import com.project.foodCourt.application.dto.response.RestaurantResponseDto;
import com.project.foodCourt.domain.model.RestaurantModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestaurantResponseMapper {
    RestaurantResponseDto toRestaurantResponseDto(RestaurantModel restaurantModel);
    RestaurantModel toRestaurantModel(RestaurantResponseDto restaurantResponseDto);
}
