package com.project.foodCourt.domain.spi;
import com.project.foodCourt.domain.model.OrderModel;

import java.util.List;

public interface IOrderPersistencePort {
    OrderModel save(OrderModel orderModel);
    List<OrderModel> findOrdersByClientIdAndStatuses(Long clientId, List<String> statuses);
}
