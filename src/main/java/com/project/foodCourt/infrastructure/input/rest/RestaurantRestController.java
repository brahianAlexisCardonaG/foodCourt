package com.project.foodCourt.infrastructure.input.rest;

import com.project.foodCourt.application.dto.request.RestaurantRequestDto;
import com.project.foodCourt.application.dto.response.RestaurantResponseDto;
import com.project.foodCourt.application.handler.IRestaurantHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/restaurant")
@RequiredArgsConstructor
public class RestaurantRestController {
    private final IRestaurantHandler iRestaurantHandler;

    @Operation(summary = "Add a new Restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "restaurant created", content = @Content),
            @ApiResponse(responseCode = "409", description = "restaurant already exists", content = @Content)
    })
    @PostMapping
    public ResponseEntity<RestaurantResponseDto> createRestaurant(
            @Valid @RequestBody RestaurantRequestDto restaurantRequestDto
    ) {
        return ResponseEntity.ok(iRestaurantHandler.createRestaurant(restaurantRequestDto));
    }
}
