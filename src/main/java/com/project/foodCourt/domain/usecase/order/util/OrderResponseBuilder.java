package com.project.foodCourt.domain.usecase.order.util;

import com.project.foodCourt.domain.model.DishModel;
import com.project.foodCourt.domain.model.OrderModel;
import com.project.foodCourt.domain.model.RestaurantModel;
import com.project.foodCourt.domain.model.feignclient.UserRoleResponse;
import com.project.foodCourt.domain.model.modelbasic.OrderDishBasicModel;
import com.project.foodCourt.domain.model.orderresponse.OrderDishResponseModel;
import com.project.foodCourt.domain.model.orderresponse.OrderResponseModel;

import java.util.List;

public class OrderResponseBuilder {
    
    public static OrderResponseModel buildOrderResponseFromOrder(
            OrderModel savedOrder,
            UserRoleResponse userRoleResponse,
            RestaurantModel restaurant,
            List<DishModel> dishes,
            OrderModel originalOrder
    ) {
        return buildOrderResponse(savedOrder, userRoleResponse, restaurant, dishes, originalOrder.getOrderDishes());
    }
    
    public static OrderResponseModel buildOrderResponse(
            OrderModel order,
            UserRoleResponse userRoleResponse,
            RestaurantModel restaurant,
            List<DishModel> dishes,
            List<OrderDishBasicModel> orderDishes
    ) {
        OrderResponseModel response = new OrderResponseModel();
        response.setNameUser(userRoleResponse.getFirstName());
        response.setLastNameUser(userRoleResponse.getLastName());
        response.setDate(order.getDate());
        response.setStatus(order.getStatus());
        response.setNameRestaurant(restaurant.getName());
        
        List<OrderDishResponseModel> orderDishResponses = dishes.stream()
            .map(dish -> {
                OrderDishResponseModel dishResponse = new OrderDishResponseModel();
                dishResponse.setName(dish.getName());
                dishResponse.setDescription(dish.getDescription());
                dishResponse.setPrice(dish.getPrice());
                dishResponse.setImageUrl(dish.getImageUrl());
                
                Integer quantity = orderDishes.stream()
                    .filter(orderDish -> orderDish.getDishId().equals(dish.getId()))
                    .findFirst()
                    .map(OrderDishBasicModel::getQuantity)
                    .orElse(0);
                dishResponse.setQuantity(quantity);
                
                return dishResponse;
            })
            .toList();
        
        response.setOrderDishes(orderDishResponses);
        return response;
    }
    
    public static OrderResponseModel buildOrderResponseWithEmployee(
            OrderModel order,
            UserRoleResponse userRoleResponse,
            RestaurantModel restaurant,
            List<DishModel> dishes,
            List<OrderDishBasicModel> orderDishes,
            UserRoleResponse assignedEmployee
    ) {
        OrderResponseModel response = buildOrderResponse(order, userRoleResponse, restaurant, dishes, orderDishes);
        if (assignedEmployee != null) {
            response.setAssignedEmployee(assignedEmployee.getFirstName() + " " + assignedEmployee.getLastName());
        }
        return response;
    }
}