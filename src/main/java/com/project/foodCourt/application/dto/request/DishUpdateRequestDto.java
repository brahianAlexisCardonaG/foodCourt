package com.project.foodCourt.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class DishUpdateRequestDto {

    @NotNull(message = "Field id cannot be empty or null.")
    private Long id;

    @NotBlank(message = "Field description cannot be empty or null.")
    @Pattern(regexp = "^(?!\\d+$).*$", message = "Name cannot contain only numbers.")
    private String description;

    @NotNull(message = "Field price cannot be empty or null.")
    private Float price;
}
