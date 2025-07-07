package com.project.foodCourt.application.dto.response.dish;

import lombok.Data;

@Data
public class DishInfoResponseDto {
    private String name;
    private String description;
    private Float price;
    private String imageUrl;
}
