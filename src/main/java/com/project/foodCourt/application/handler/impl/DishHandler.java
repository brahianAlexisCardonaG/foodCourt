package com.project.foodCourt.application.handler.impl;

import com.project.foodCourt.application.dto.request.dish.DishEnableDisableRequestDto;
import com.project.foodCourt.application.dto.request.dish.DishRequestDto;
import com.project.foodCourt.application.dto.request.dish.DishUpdateRequestDto;
import com.project.foodCourt.application.dto.response.dish.DishResponseDto;
import com.project.foodCourt.application.handler.IDishHandler;
import com.project.foodCourt.application.mapper.dish.IDishRequestMapper;
import com.project.foodCourt.application.mapper.dish.IDishResponseMapper;
import com.project.foodCourt.domain.api.IDishServicePort;
import com.project.foodCourt.domain.model.DishModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DishHandler implements IDishHandler {
    private final IDishServicePort iDishServicePort;
    private final IDishRequestMapper iDishRequestMapper;
    private final IDishResponseMapper iDishResponseMapper;

    @Override
    public DishResponseDto createDish(DishRequestDto dishRequestDto) {
        DishModel dishModel = iDishRequestMapper.toDishModel(dishRequestDto);
        DishModel dishModelCreated = iDishServicePort.createDish(dishModel);
        return iDishResponseMapper
                .toDishResponseDto(dishModelCreated);
    }

    @Override
    public DishResponseDto updateDish(DishUpdateRequestDto dishUpdateRequestDto) {
        DishModel dishModel = iDishRequestMapper.toDishModelUpdate(dishUpdateRequestDto);
        DishModel dishModelUpdated = iDishServicePort.updateDish(dishModel);
        return iDishResponseMapper
                .toDishResponseDto(dishModelUpdated);
    }

    @Override
    public DishResponseDto enableDisableDish(DishEnableDisableRequestDto dishEnableDisableRequestDto) {
        DishModel dishModel = iDishRequestMapper.toDishModelEnableDisable(dishEnableDisableRequestDto);
        DishModel dishModelUpdated = iDishServicePort.disableEnableDish(dishModel);
        return iDishResponseMapper
                .toDishResponseDto(dishModelUpdated);
    }
}
