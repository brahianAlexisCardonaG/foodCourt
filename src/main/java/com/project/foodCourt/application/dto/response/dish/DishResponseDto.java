package com.project.foodCourt.application.dto.response.dish;

import com.project.foodCourt.application.dto.response.restaurant.RestaurantSummaryDto;
import lombok.Data;

@Data
public class DishResponseDto {
    private Long id;
    private String name;
    private Long categoryId;
    private String description;
    private Float price;
    private RestaurantSummaryDto restaurant;
    private String imageUrl;
    private Boolean active;
}
