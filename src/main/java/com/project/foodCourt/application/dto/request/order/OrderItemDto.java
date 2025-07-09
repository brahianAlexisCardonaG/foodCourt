package com.project.foodCourt.application.dto.request.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OrderItemDto {
    @NotNull(message = "Field dishId cannot be empty or null.")
    @Positive(message = "Field dishId must be a positive number.")
    private Long dishId;

    @Min(1)
    @NotNull(message = "Field quantity cannot be empty or null.")
    @Positive(message = "Field quantity must be a positive number.")
    private Integer quantity;
}
