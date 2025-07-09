package com.project.foodCourt.infrastructure.out.jpa.adapter;

import com.project.foodCourt.domain.model.OrderDishModel;
import com.project.foodCourt.domain.model.OrderModel;
import com.project.foodCourt.domain.spi.IOrderPersistencePort;
import com.project.foodCourt.infrastructure.out.jpa.entity.DishEntity;
import com.project.foodCourt.infrastructure.out.jpa.entity.OrderEntity;
import com.project.foodCourt.infrastructure.out.jpa.mapper.IOrderEntityMapper;
import com.project.foodCourt.infrastructure.out.jpa.repository.IOrderRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderJpaAdapter implements IOrderPersistencePort {

    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;
    private final EntityManager entityManager;

    @Override
    public OrderModel save(OrderModel orderModel) {
        OrderEntity orderEntity = orderEntityMapper.toOrderEntity(orderModel);
        
        // Establecer referencias JPA para DishEntity en cada OrderDishEntity
        if (orderEntity.getOrderDishes() != null) {
            orderEntity.getOrderDishes().forEach(orderDish -> {
                Long dishId = orderDish.getId().getDishId();
                DishEntity dishReference = entityManager.getReference(DishEntity.class, dishId);
                orderDish.setDishes(dishReference);
            });
        }
        
        OrderEntity savedEntity = orderRepository.save(orderEntity);
        return orderEntityMapper.toOrderModel(savedEntity);
    }

    @Override
    public List<OrderModel> findOrdersByClientIdAndStatuses(Long clientId, List<String> statuses) {
        List<OrderEntity> orders = orderRepository.findByClientIdAndStatusIn(clientId, statuses);
        return orders.stream()
            .map(orderEntityMapper::toOrderModel)
            .toList();
    }
}