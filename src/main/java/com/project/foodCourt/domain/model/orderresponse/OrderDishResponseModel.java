package com.project.foodCourt.domain.model.orderresponse;

import lombok.Data;

@Data
public class OrderDishResponseModel {
    private String name;
    private String description;
    private Float price;
    private String imageUrl;
    private Integer quantity;
}
