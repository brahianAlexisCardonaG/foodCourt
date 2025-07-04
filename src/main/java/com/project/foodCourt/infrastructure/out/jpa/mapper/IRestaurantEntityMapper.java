package com.project.foodCourt.infrastructure.out.jpa.mapper;

import com.project.foodCourt.domain.model.RestaurantModel;
import com.project.foodCourt.infrastructure.out.jpa.entity.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestaurantEntityMapper {
    RestaurantModel toRestaurantModel(RestaurantEntity restaurantEntity);
    RestaurantEntity toRestaurantEntity(RestaurantModel restaurantModel);
}
