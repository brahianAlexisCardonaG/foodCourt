package com.project.foodCourt.domain.model;

import com.project.foodCourt.domain.model.modelbasic.CategoryBasicModel;
import com.project.foodCourt.domain.model.modelbasic.RestaurantBasicModel;
import lombok.Data;

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
}
