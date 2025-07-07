package com.project.foodCourt.infrastructure.input.rest;

import com.project.foodCourt.application.dto.request.dish.DishEnableDisableRequestDto;
import com.project.foodCourt.application.dto.request.dish.DishRequestDto;
import com.project.foodCourt.application.dto.request.dish.DishUpdateRequestDto;
import com.project.foodCourt.application.dto.response.dish.DishResponseDto;
import com.project.foodCourt.application.handler.IDishHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "Update a Dish")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "dish updated", content = @Content),
      })
    @PatchMapping
    public ResponseEntity<DishResponseDto> updateDish(
            @Valid @RequestBody DishUpdateRequestDto dishUpdateRequestDto
    ) {
        return ResponseEntity.ok(iDishHandler.updateDish(dishUpdateRequestDto));
    }

    @Operation(summary = "Enable or Disable a Dish")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "dish updated", content = @Content),
    })
    @PatchMapping("/active")
    public ResponseEntity<DishResponseDto> enableDisableDish(
            @Valid @RequestBody DishEnableDisableRequestDto dishEnableDisableRequestDto
    ) {
        return ResponseEntity.ok(iDishHandler.enableDisableDish(dishEnableDisableRequestDto));
    }
}
