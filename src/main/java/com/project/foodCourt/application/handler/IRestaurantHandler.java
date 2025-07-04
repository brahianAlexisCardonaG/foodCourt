package com.project.foodCourt.application.handler;

import com.project.foodCourt.application.dto.request.RestaurantRequestDto;
import com.project.foodCourt.application.dto.response.RestaurantResponseDto;

public interface IRestaurantHandler {
    RestaurantResponseDto createRestaurant(RestaurantRequestDto restaurantRequestDto);
}
