package com.project.foodCourt.domain.model.modelbasic.mapper;

import com.project.foodCourt.domain.model.RestaurantModel;
import com.project.foodCourt.domain.model.modelbasic.RestaurantBasicModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestaurantBasicModelMapper {
    RestaurantBasicModel toRestaurantBasicModel(RestaurantModel restaurantModel);
    RestaurantModel toRestaurantModel(RestaurantBasicModel restaurantBasicModel);
}
