package com.project.foodCourt.infrastructure.out.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders_dishes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDishEntity {
    @EmbeddedId
    private OrderDishIdEntity id = new OrderDishIdEntity();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("dishId")
    @JoinColumn(name = "dish_id", nullable = false)
    private DishEntity dishes;

    @Column(nullable = false)
    private Integer quantity;
}
