package com.project.foodCourt.infrastructure.out.jpa.repository;

import com.project.foodCourt.infrastructure.out.jpa.entity.DishEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IDishRepository extends JpaRepository<DishEntity, Long> {
    Optional<DishEntity> findByName(String name);
    
    @Query("SELECT d FROM DishEntity d WHERE d.restaurant.id = :restaurantId " +
           "AND (:categoryId IS NULL OR d.category.id = :categoryId) " +
           "AND d.active = true")
    Page<DishEntity> findByRestaurantIdAndOptionalCategoryId(
            Pageable pageable, 
            @Param("restaurantId") Long restaurantId, 
            @Param("categoryId") Long categoryId);
}
