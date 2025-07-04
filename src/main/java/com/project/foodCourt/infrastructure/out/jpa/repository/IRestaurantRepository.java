package com.project.foodCourt.infrastructure.out.jpa.repository;

import com.project.foodCourt.infrastructure.out.jpa.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
    Optional<RestaurantEntity> findByName(String name);
    Optional<RestaurantEntity> findByNit(String nit);
}
