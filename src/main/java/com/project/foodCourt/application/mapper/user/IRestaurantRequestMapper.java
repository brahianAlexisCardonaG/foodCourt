package com.project.foodCourt.application.mapper.user;

import com.project.foodCourt.application.dto.request.RestaurantRequestDto;
import com.project.foodCourt.domain.model.RestaurantModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestaurantRequestMapper {
    RestaurantRequestDto toRestaurantRequestDto(RestaurantModel restaurantModel);
    RestaurantModel toRestaurantModel(RestaurantRequestDto restaurantRequestDto);
}
