package com.project.foodCourt.domain.model.modelbasic;

import lombok.Data;

@Data
public class RestaurantBasicModel {
    private Long id;
    private String name;
    private String address;
    private Long ownerId;
    private String phone;
    private String logoUrl;
    private String nit;
}