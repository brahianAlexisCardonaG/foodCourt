package com.project.foodCourt.domain.model.modelbasic;

import lombok.Data;

@Data
public class DishBasicModel {
    private Long id;
    private String name;
    private String description;
    private Float price;
    private String imageUrl;
    private Boolean active;
}