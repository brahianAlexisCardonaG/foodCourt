package com.project.foodCourt.infrastructure.out.jpa.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDishIdEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long orderId;
    private Long dishId;
}
