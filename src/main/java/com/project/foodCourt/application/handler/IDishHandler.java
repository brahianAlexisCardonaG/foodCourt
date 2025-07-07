package com.project.foodCourt.application.handler;

import com.project.foodCourt.application.dto.request.dish.DishEnableDisableRequestDto;
import com.project.foodCourt.application.dto.request.dish.DishRequestDto;
import com.project.foodCourt.application.dto.request.dish.DishUpdateRequestDto;
import com.project.foodCourt.application.dto.response.dish.DishResponseDto;

public interface IDishHandler {
    DishResponseDto createDish(DishRequestDto dishRequestDto);
    DishResponseDto updateDish(DishUpdateRequestDto dishUpdateRequestDto);
    DishResponseDto enableDisableDish(DishEnableDisableRequestDto dishEnableDisableRequestDto);
}
