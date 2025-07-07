package com.project.foodCourt.application.dto.request.dish;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class DishEnableDisableRequestDto {
    @NotNull(message = "Field id cannot be empty or null.")
    @Positive(message = "Field id must be a positive number.")
    private Long id;
    
    @NotNull(message = "Field userId cannot be empty or null.")
    @Positive(message = "Field userId must be a positive number.")
    private Long userId;
    
    @NotNull(message = "Field active cannot be empty or null.")
    private Boolean active;
}
