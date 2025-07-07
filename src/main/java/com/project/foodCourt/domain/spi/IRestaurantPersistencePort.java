package com.project.foodCourt.domain.spi;

import com.project.foodCourt.domain.model.RestaurantModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IRestaurantPersistencePort {
    RestaurantModel save(RestaurantModel restaurantModel);
    Optional<RestaurantModel> getRestaurantByName(String name);
    Optional<RestaurantModel> getRestaurantByNit(String nit);
    Optional<RestaurantModel> getRestaurantById(Long id);
    Page<RestaurantModel> getAllRestaurants(Pageable pageable);
}
