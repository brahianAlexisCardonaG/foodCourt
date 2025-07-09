package com.project.foodCourt.application.handler.impl;

import com.project.foodCourt.application.dto.request.order.OrderRequestDto;
import com.project.foodCourt.application.dto.response.order.OrderResponseDto;
import com.project.foodCourt.application.handler.IOrderHandler;
import com.project.foodCourt.application.mapper.order.IOrderRequestMapper;
import com.project.foodCourt.application.mapper.order.IOrderResponseMapper;
import com.project.foodCourt.domain.api.IOrderServicePort;
import com.project.foodCourt.domain.model.OrderModel;
import com.project.foodCourt.domain.model.orderresponse.OrderResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderHandler implements IOrderHandler {

    private final IOrderServicePort iOrderServicePort;
    private final IOrderRequestMapper iOrderRequestMapper;
    private final IOrderResponseMapper iOrderResponseMapper;

    @Override
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        OrderModel orderModel = iOrderRequestMapper.toOrderDishModel(orderRequestDto);
        OrderResponseModel orderModelCreated = iOrderServicePort.createOrder(orderModel);
        return iOrderResponseMapper.toOrderResponseDto(orderModelCreated);
    }
}
