package com.project.foodCourt.domain.model;

import com.project.foodCourt.domain.model.modelbasic.CategoryBasicModel;
import com.project.foodCourt.domain.model.modelbasic.OrderDishBasicModel;
import com.project.foodCourt.domain.model.modelbasic.RestaurantBasicModel;
import com.project.foodCourt.infrastructure.out.jpa.entity.OrderDishEntity;
import lombok.Data;

import java.util.List;

@Data
public class DishModel {
    private Long id;
    private String name;
    private CategoryBasicModel category;
    private String description;
    private Float price;
    private RestaurantBasicModel restaurant;
    private String imageUrl;
    private Boolean active;
    private List<OrderDishBasicModel> orderDishes;
}
