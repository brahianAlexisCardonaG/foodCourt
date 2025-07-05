package com.project.foodCourt.application.handler;

import com.project.foodCourt.application.dto.request.DishRequestDto;
import com.project.foodCourt.application.dto.response.DishResponseDto;

public interface IDishHandler {
    DishResponseDto createDish(DishRequestDto dishRequestDto);
}
