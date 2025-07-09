package com.project.foodCourt.application.dto.request.order;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDto {

    @NotNull(message = "Field clientId cannot be empty or null.")
    @Positive(message = "Field clientId must be a positive number.")
    private Long clientId;

    @NotNull(message = "Field restaurantId cannot be empty or null.")
    @Positive(message = "Field restaurantId must be a positive number.")
    private Long restaurantId;

    @NotEmpty
    private List<OrderItemDto> items;
}
