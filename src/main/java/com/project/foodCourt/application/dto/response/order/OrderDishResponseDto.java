package com.project.foodCourt.application.dto.response.order;

import lombok.Data;

@Data
public class OrderDishResponseDto {
    private String name;
    private String description;
    private Float price;
    private String imageUrl;
    private Integer quantity;
}
