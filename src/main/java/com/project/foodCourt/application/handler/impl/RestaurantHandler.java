package com.project.foodCourt.application.handler.impl;

import com.project.foodCourt.application.dto.request.restaurant.RestaurantRequestDto;
import com.project.foodCourt.application.dto.response.restaurant.RestaurantInfoResponseDto;
import com.project.foodCourt.application.dto.response.restaurant.RestaurantResponseDto;
import com.project.foodCourt.application.handler.IRestaurantHandler;
import com.project.foodCourt.application.mapper.restaurant.IRestaurantRequestMapper;
import com.project.foodCourt.application.mapper.restaurant.IRestaurantResponseMapper;
import com.project.foodCourt.domain.api.IRestaurantServicePort;
import com.project.foodCourt.domain.model.RestaurantModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantHandler implements IRestaurantHandler {

    private final IRestaurantServicePort iRestaurantServicePort;
    private final IRestaurantResponseMapper iRestaurantResponseMapper;
    private final IRestaurantRequestMapper iRestaurantRequestMapper;

    @Override
    public RestaurantResponseDto createRestaurant(RestaurantRequestDto restaurantRequestDto) {
        RestaurantModel restaurantModel = iRestaurantRequestMapper.toRestaurantModel(restaurantRequestDto);
        RestaurantModel restaurantModelCreated = iRestaurantServicePort.createRestaurant(restaurantModel);
        return iRestaurantResponseMapper
                .toRestaurantResponseDto(restaurantModelCreated);
    }

    @Override
    public Page<RestaurantInfoResponseDto> getAllRestaurants(Pageable pageable) {
        Page<RestaurantModel> restaurantPage = iRestaurantServicePort.getAllRestaurants(pageable);
        return restaurantPage.map(iRestaurantResponseMapper::toRestaurantInfoResponseDto);
    }
}
