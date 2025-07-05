package com.project.foodCourt.application.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
