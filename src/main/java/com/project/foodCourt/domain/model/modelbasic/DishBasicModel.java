package com.project.foodCourt.domain.model.modelbasic;

import lombok.Data;

@Data
public class DishBasicModel {
    private Long id;
    private String name;
    private Long categoryId;
    private String description;
    private Float price;
    private RestaurantBasicModel restaurant;
    private String imageUrl;
    private Boolean active;
}