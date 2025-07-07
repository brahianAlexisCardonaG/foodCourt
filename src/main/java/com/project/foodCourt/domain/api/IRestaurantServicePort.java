package com.project.foodCourt.domain.api;
import com.project.foodCourt.domain.model.RestaurantModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IRestaurantServicePort {
    RestaurantModel createRestaurant(RestaurantModel restaurantModel);
    Page<RestaurantModel> getAllRestaurants(Pageable pageable);
}
