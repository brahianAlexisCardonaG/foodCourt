package com.project.foodCourt.domain.model;

import com.project.foodCourt.domain.model.modelbasic.DishBasicModel;
import lombok.Data;

import java.util.List;

@Data
public class CategoryModel {
    private Long id;
    private String name;
    private String description;
    private List<DishBasicModel> dishes;
}
