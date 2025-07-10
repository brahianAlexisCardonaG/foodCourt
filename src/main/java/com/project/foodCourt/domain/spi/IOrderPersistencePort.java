package com.project.foodCourt.domain.spi;
import com.project.foodCourt.domain.model.OrderModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IOrderPersistencePort {
    OrderModel save(OrderModel orderModel);
    List<OrderModel> findOrdersByClientIdAndStatuses(Long clientId, List<String> statuses);
    Page<OrderModel> findOrdersByStatus(String status, Pageable pageable);
    Page<OrderModel> findAllOrders(Pageable pageable);
    Optional<OrderModel> findById(Long id);
}
