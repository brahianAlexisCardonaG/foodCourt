package com.project.foodCourt.domain.model;

import com.project.foodCourt.domain.model.modelbasic.DishBasicModel;
import com.project.foodCourt.domain.model.modelbasic.OrderBasicModel;
import lombok.Data;

@Data
public class OrderDishModel {
    private OrderBasicModel orders;
    private DishBasicModel dishes;
    private Integer quantity;
}
