package com.project.foodCourt.application.handler.impl;

import com.project.foodCourt.application.dto.request.order.OrderRequestDto;
import com.project.foodCourt.application.dto.response.order.OrderBasicResponseDto;
import com.project.foodCourt.application.dto.response.order.OrderResponseDto;
import com.project.foodCourt.application.handler.IOrderHandler;
import com.project.foodCourt.application.mapper.order.IOrderRequestMapper;
import com.project.foodCourt.application.mapper.order.IOrderResponseMapper;
import com.project.foodCourt.domain.api.IOrderServicePort;
import com.project.foodCourt.domain.model.OrderModel;
import com.project.foodCourt.domain.model.orderresponse.OrderResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Page<OrderResponseDto> getOrdersByStatus(String status, Pageable pageable) {
        Page<OrderResponseModel> orderPage = iOrderServicePort.getOrdersByStatus(status,pageable);
        return orderPage.map(iOrderResponseMapper::toOrderResponseDto);
    }

    @Override
    public Page<OrderResponseDto> getAllOrders(Pageable pageable) {
        Page<OrderResponseModel> orderPage = iOrderServicePort.getAllOrders(pageable);
        return orderPage.map(iOrderResponseMapper::toOrderResponseDto);
    }

    @Override
    public OrderResponseDto assignedEmployeeIdToOrder(Long orderId, Long employeeId) {
        OrderResponseModel orderResponseModel = iOrderServicePort
                .assignedEmployeeIdToOrder(orderId, employeeId);
        return iOrderResponseMapper.toOrderResponseDto(orderResponseModel);
    }

    @Override
    public OrderBasicResponseDto updateStatusOrderToReady(Long orderId, Long employeeId) {
        OrderModel orderModel = iOrderServicePort.updateStatusOrderToReady(orderId, employeeId);
        return iOrderResponseMapper.toOrderBasicResponseDto(orderModel);
    }
}
