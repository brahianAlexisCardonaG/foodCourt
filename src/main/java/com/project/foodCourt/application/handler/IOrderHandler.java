package com.project.foodCourt.application.handler;

import com.project.foodCourt.application.dto.request.order.OrderRequestDto;
import com.project.foodCourt.application.dto.response.order.OrderBasicResponseDto;
import com.project.foodCourt.application.dto.response.order.OrderResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOrderHandler {
    OrderResponseDto createOrder(OrderRequestDto orderRequestDto);
    Page<OrderResponseDto> getOrdersByStatus(String status, Pageable pageable);
    Page<OrderResponseDto> getAllOrders(Pageable pageable);
    OrderResponseDto assignedEmployeeIdToOrder(Long orderId, Long employeeId);
    OrderBasicResponseDto updateStatusOrderToReady(Long orderId, Long employeeId);

}
