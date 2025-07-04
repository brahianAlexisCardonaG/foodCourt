package com.project.foodCourt.domain.model;

import lombok.Data;

@Data
public class RestaurantModel {
    private Long id;
    private String name;
    private String address;
    private Long ownerId;
    private String phone;
    private String logoUrl;
    private String nit;
}
