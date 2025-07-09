package com.project.foodCourt.domain.model.orderresponse;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class OrderResponseModel {
    private String nameUser;
    private String lastNameUser;
    private LocalDate date;
    private String status;
    private String nameRestaurant;
    private List<OrderDishResponseModel> orderDishes;
}
