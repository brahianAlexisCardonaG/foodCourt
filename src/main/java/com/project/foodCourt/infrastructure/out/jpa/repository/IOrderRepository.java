package com.project.foodCourt.infrastructure.out.jpa.repository;

import com.project.foodCourt.infrastructure.out.jpa.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByClientIdAndStatusIn(Long clientId, List<String> statuses);
}