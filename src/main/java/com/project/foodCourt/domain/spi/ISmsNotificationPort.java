package com.project.foodCourt.domain.spi;

import com.project.foodCourt.domain.model.orderresponse.OrderDishResponseModel;

import java.util.List;

public interface ISmsNotificationPort {
    void sendOrderReadyNotification(String phoneNumber,
                                    Long orderId,
                                    List<OrderDishResponseModel> orderDishResponseModel,
                                    String securityPin);
}