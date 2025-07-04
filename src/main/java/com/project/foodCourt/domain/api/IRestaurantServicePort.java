package com.project.foodCourt.domain.api;
import com.project.foodCourt.domain.model.RestaurantModel;

public interface IRestaurantServicePort {
    RestaurantModel createRestaurant(RestaurantModel restaurantModel);
}
