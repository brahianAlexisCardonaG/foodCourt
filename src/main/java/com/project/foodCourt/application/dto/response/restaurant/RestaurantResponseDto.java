package com.project.foodCourt.application.dto.response.restaurant;

import lombok.Data;

@Data
public class RestaurantResponseDto {
    private Long id;
    private String name;
    private String address;
    private Long ownerId;
    private String phone;
    private String logoUrl;
    private String nit;
}
