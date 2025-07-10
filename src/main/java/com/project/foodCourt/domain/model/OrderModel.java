package com.project.foodCourt.domain.model;

import com.project.foodCourt.domain.model.modelbasic.OrderDishBasicModel;
import com.project.foodCourt.domain.model.modelbasic.RestaurantBasicModel;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OrderModel {
    private Long id;
    private Long clientId;
    private LocalDate date;
    private String status;
    private Long chefId;
    private Long assignedEmployeeId;
    private RestaurantBasicModel restaurant;
    private List<OrderDishBasicModel> orderDishes;
}
