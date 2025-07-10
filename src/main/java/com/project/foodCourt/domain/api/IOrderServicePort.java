package com.project.foodCourt.domain.api;

import com.project.foodCourt.domain.model.OrderModel;
import com.project.foodCourt.domain.model.orderresponse.OrderResponseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOrderServicePort {
    OrderResponseModel createOrder(OrderModel orderModel);
    Page<OrderResponseModel> getOrdersByStatus(String status, Pageable pageable);
    Page<OrderResponseModel> getAllOrders(Pageable pageable);
    OrderResponseModel assignedEmployeeIdToOrder(Long orderId, Long employeeId);
    OrderModel updateStatusOrderToReady(Long orderId, Long employeeId);
}
