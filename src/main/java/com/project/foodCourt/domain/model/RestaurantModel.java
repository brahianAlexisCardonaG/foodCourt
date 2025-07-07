package com.project.foodCourt.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.foodCourt.domain.model.modelbasic.DishBasicModel;
import lombok.Data;

import java.util.List;

@Data
public class RestaurantModel {
    private Long id;
    private String name;
    private String address;
    private Long ownerId;
    private String phone;
    private String logoUrl;
    private String nit;
    @JsonIgnore
    private List<DishBasicModel> dishes;
}
