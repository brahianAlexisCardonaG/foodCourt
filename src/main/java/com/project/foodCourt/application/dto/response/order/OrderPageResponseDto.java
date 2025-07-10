package com.project.foodCourt.application.dto.response.order;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderPageResponseDto {
    private List<OrderResponseDto> content;
    private int page;
    private int size;
    private long totalElements;
}
