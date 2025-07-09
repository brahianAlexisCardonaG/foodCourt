package com.project.foodCourt.domain.model;

import com.project.foodCourt.domain.model.modelbasic.DishBasicModel;
import lombok.Data;

@Data
public class OrderDishModel {
    private OrderModel orders;
    private DishBasicModel dishes;
    private Integer quantity;
}
