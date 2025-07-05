package com.project.foodCourt.utils;

import lombok.Getter;

@Getter
public enum ErrorCatalog {

    USER_NOT_FOUND("ERR_USER_001", "User not found."),
    USER_NOT_OWNER("ERR_USER_002", "The user is not an owner."),
    INVALID_RESTAURANT("ERR_RESTAURANT_002", "Invalid restaurant parameters."),
    GENERIC_ERROR("ERR_GEN_001", "An unexpected error occurred."),
    RESTAURANT_ALREADY_EXISTS("ERR_RESTAURANT_003", "The restaurant already exists."),
    RESTAURANT_NOT_FOUND("ERR_RESTAURANT_004", "The restaurant not found.");

    private final String code;
    private final String message;

    ErrorCatalog(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
