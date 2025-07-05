package com.project.foodCourt.domain.spi;

import com.project.foodCourt.domain.model.RestaurantModel;

import java.util.Optional;

public interface IRestaurantPersistencePort {
    RestaurantModel save(RestaurantModel restaurantModel);
    Optional<RestaurantModel> getRestaurantByName(String name);
    Optional<RestaurantModel> getRestaurantByNit(String nit);
    Optional<RestaurantModel> getRestaurantById(Long id);
}
