package com.project.foodCourt.infrastructure.out.jpa.adapter;

import com.project.foodCourt.domain.model.OrderDishModel;
import com.project.foodCourt.domain.spi.IOrderDishPersistencePort;
import com.project.foodCourt.infrastructure.out.jpa.entity.OrderDishEntity;
import com.project.foodCourt.infrastructure.out.jpa.mapper.IOrderDishEntityMapper;
import com.project.foodCourt.infrastructure.out.jpa.repository.IOrderDishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderDishJpaAdapter implements IOrderDishPersistencePort {

    private final IOrderDishRepository orderDishRepository;
    private final IOrderDishEntityMapper orderDishEntityMapper;

    @Override
    public List<OrderDishModel> findByOrderId(Long orderId) {
        List<OrderDishEntity> entities = orderDishRepository.findByIdOrderId(orderId);
        return entities.stream()
            .map(orderDishEntityMapper::toOrderDishModel)
            .toList();
    }
}