package com.project.foodCourt.domain.api;

import com.project.foodCourt.domain.model.OrderDishModel;
import com.project.foodCourt.domain.model.OrderModel;
import com.project.foodCourt.domain.model.orderresponse.OrderResponseModel;

public interface IOrderServicePort {
    OrderResponseModel createOrder(OrderModel orderModel);
}
