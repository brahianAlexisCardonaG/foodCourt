package com.project.foodCourt.application.dto.response.order;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OrderResponseDto {
    private String nameUser;
    private String lastNameUser;
    private LocalDate date;
    private String status;
    private String nameRestaurant;
    private List<OrderDishResponseDto> orderDishes;
}
