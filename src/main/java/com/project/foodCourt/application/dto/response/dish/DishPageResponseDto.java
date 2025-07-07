package com.project.foodCourt.application.dto.response.dish;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DishPageResponseDto {
    private List<DishInfoResponseDto> content;
    private int page;
    private int size;
    private long totalElements;
}
