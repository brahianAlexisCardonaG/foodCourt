package com.project.foodCourt.utils;

import lombok.Getter;

@Getter
public enum ErrorCatalog {
    //user
    USER_NOT_FOUND("ERR_USER_001", "User not found."),
    USER_NOT_OWNER("ERR_USER_002", "The user is not an owner."),
    USER_NOT_EMPLOYEE("ERR_USER_003", "The user is not an employee."),

    //restaurant
    INVALID_RESTAURANT("ERR_RESTAURANT_002", "Invalid restaurant parameters."),
    GENERIC_ERROR("ERR_GEN_001", "An unexpected error occurred."),
    RESTAURANT_ALREADY_EXISTS("ERR_RESTAURANT_003", "The restaurant already exists."),
    RESTAURANT_NOT_FOUND("ERR_RESTAURANT_004", "The restaurant not found."),
    RESTAURANT_NOT_OWNER("ERR_RESTAURANT_005", "The user is not an owner of the restaurant."),

    //dish
    DISH_NOT_FOUND("ERR_DISH_001", "The dish not found."),
    DISH_ALREADY_EXISTS("ERR_DISH_002", "The dish already exists."),
    INVALID_DISH("ERR_DISH_003", "Invalid dish parameters."),

    //category
    CATEGORY_NOT_FOUND("ERR_RESTAURANT_001", "The category not found."),
    
    //order
    CLIENT_HAS_ACTIVE_ORDER("ERR_ORDER_001", "Client has an active order in process."),
    DISH_NOT_FROM_RESTAURANT("ERR_ORDER_002", "Dish does not belong to the specified restaurant."),
    ORDER_NOT_FOUND("ERR_ORDER_003", "The order not found.")
    ;

    private final String code;
    private final String message;

    ErrorCatalog(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
