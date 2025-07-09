package com.project.foodCourt.domain.spi;

import com.project.foodCourt.domain.model.OrderDishModel;
import java.util.List;

public interface IOrderDishPersistencePort {
    List<OrderDishModel> findByOrderId(Long orderId);
}