package com.project.foodCourt.infrastructure.input.rest;

import com.project.foodCourt.application.dto.request.restaurant.RestaurantRequestDto;
import com.project.foodCourt.application.dto.response.restaurant.RestaurantInfoResponseDto;
import com.project.foodCourt.application.dto.response.restaurant.RestaurantPageResponseDto;
import com.project.foodCourt.application.dto.response.restaurant.RestaurantResponseDto;
import com.project.foodCourt.application.handler.IRestaurantHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "Get List Of Restaurants")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurants List Found", content = @Content)
    })
    @GetMapping("/client-restaurants")
    public ResponseEntity<RestaurantPageResponseDto> getAllRestaurants(Pageable pageable) {
        Page<RestaurantInfoResponseDto> page = iRestaurantHandler.getAllRestaurants(pageable);
        RestaurantPageResponseDto response = new RestaurantPageResponseDto(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements()
        );
        return ResponseEntity.ok(response);
    }
}
