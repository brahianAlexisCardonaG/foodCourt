package com.project.foodCourt.application.dto.request.dish;

import lombok.Data;

@Data
public class DishEnableDisableRequestDto {
    private Long id;
    private Long userId;
    private Boolean active;
}
