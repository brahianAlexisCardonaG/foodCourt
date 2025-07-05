package com.project.foodCourt.infrastructure.input.rest;

import com.project.foodCourt.application.dto.request.DishRequestDto;
import com.project.foodCourt.application.dto.request.RestaurantRequestDto;
import com.project.foodCourt.application.dto.response.DishResponseDto;
import com.project.foodCourt.application.dto.response.RestaurantResponseDto;
import com.project.foodCourt.application.handler.IDishHandler;
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
@RequestMapping("/api/v1/dish")
@RequiredArgsConstructor
public class DishRestController {

    private final IDishHandler iDishHandler;

    @Operation(summary = "Add a new Dish")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "dish created", content = @Content),
            @ApiResponse(responseCode = "409", description = "dish already exists", content = @Content)
    })
    @PostMapping
    public ResponseEntity<DishResponseDto> createDish(
            @Valid @RequestBody DishRequestDto dishRequestDto
    ) {
        return ResponseEntity.ok(iDishHandler.createDish(dishRequestDto));
    }
}
