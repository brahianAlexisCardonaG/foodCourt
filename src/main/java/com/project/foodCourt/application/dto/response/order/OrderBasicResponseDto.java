package com.project.foodCourt.application.dto.response.order;

import lombok.Data;

import java.time.LocalDate;

@Data
public class OrderBasicResponseDto {
    private Long id;
    private Long clientId;
    private LocalDate date;
    private String status;
    private Long assignedEmployeeId;
    private Long restaurantId;
}
