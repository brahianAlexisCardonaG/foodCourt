package com.project.foodCourt.application.handler;

import com.project.foodCourt.application.dto.request.restaurant.RestaurantRequestDto;
import com.project.foodCourt.application.dto.response.restaurant.RestaurantInfoResponseDto;
import com.project.foodCourt.application.dto.response.restaurant.RestaurantResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRestaurantHandler {
    RestaurantResponseDto createRestaurant(RestaurantRequestDto restaurantRequestDto);
    Page<RestaurantInfoResponseDto> getAllRestaurants(Pageable pageable);
}
