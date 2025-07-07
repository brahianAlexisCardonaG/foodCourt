package com.project.foodCourt.application.dto.response.restaurant;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RestaurantPageResponseDto {
    private List<RestaurantInfoResponseDto> content;
    private int page;
    private int size;
    private long totalElements;
}