package com.project.foodCourt.infrastructure.out.jpa.repository;

import com.project.foodCourt.infrastructure.out.jpa.entity.DishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDishRepository extends JpaRepository<DishEntity, Long> {
}
