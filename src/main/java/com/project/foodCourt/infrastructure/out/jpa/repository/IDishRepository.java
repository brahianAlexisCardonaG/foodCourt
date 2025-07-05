package com.project.foodCourt.infrastructure.out.jpa.repository;

import com.project.foodCourt.infrastructure.out.jpa.entity.DishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IDishRepository extends JpaRepository<DishEntity, Long> {
    Optional<DishEntity> findByName(String name);
}
