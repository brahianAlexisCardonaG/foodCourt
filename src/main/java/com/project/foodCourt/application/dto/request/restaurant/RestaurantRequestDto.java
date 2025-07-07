package com.project.foodCourt.application.dto.request.restaurant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RestaurantRequestDto {

    @NotBlank(message = "Field name cannot be empty or null.")
    @Pattern(regexp = "^(?!\\d+$).*$", message = "Name cannot contain only numbers.")
    private String name;

    @NotBlank(message = "Field address cannot be empty or null.")
    private String address;

    @NotNull(message = "Field ownerId cannot be empty or null.")
    private Long ownerId;

    @Pattern(regexp = "^\\+?[0-9]{1,13}$", message = "Phone must contain maximum 13 characters and can include + symbol.")
    @NotBlank(message = "Field phone cannot be empty or null.")
    private String phone;

    @NotBlank(message = "Field logoUrl cannot be empty or null.")
    private String logoUrl;

    @Pattern(regexp = "[0-9]{1,20}$", message = "Nit must contain maximum 20 characters.")
    @NotBlank(message = "Field nit cannot be empty or null.")
    private String nit;
}