package com.project.foodCourt.application.dto.request.dish;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class DishRequestDto {

    @NotBlank(message = "Field name cannot be empty or null.")
    @Pattern(regexp = "^(?!\\d+$).*$", message = "Name cannot contain only numbers.")
    private String name;

    @NotNull(message = "Field categoryId cannot be empty or null.")
    private Long categoryId;

    @NotBlank(message = "Field description cannot be empty or null.")
    @Pattern(regexp = "^(?!\\d+$).*$", message = "Name cannot contain only numbers.")
    private String description;

    @NotNull(message = "Field price cannot be empty or null.")
    private Float price;

    @NotNull(message = "Field restaurantId cannot be empty or null.")
    private Long restaurantId;

    @NotBlank(message = "Field imageUrl cannot be empty or null.")
    private String imageUrl;
}
