package com.project.foodCourt.infrastructure.input.rest;

import com.project.foodCourt.application.dto.request.dish.DishEnableDisableRequestDto;
import com.project.foodCourt.application.dto.request.dish.DishRequestDto;
import com.project.foodCourt.application.dto.request.dish.DishUpdateRequestDto;
import com.project.foodCourt.application.dto.response.dish.DishInfoResponseDto;
import com.project.foodCourt.application.dto.response.dish.DishPageResponseDto;
import com.project.foodCourt.application.dto.response.dish.DishResponseDto;
import com.project.foodCourt.application.dto.response.restaurant.RestaurantInfoResponseDto;
import com.project.foodCourt.application.dto.response.restaurant.RestaurantPageResponseDto;
import com.project.foodCourt.application.handler.IDishHandler;
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

    @Operation(summary = "Get List Of Dish By RestaurantId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get List Of Dish By RestaurantId Found", content = @Content)
    })
    @GetMapping("/client-dishes-restaurant")
    public ResponseEntity<DishPageResponseDto> getAllRestaurants(Pageable pageable,
                                                                       @RequestParam Long restaurantId,
                                                                       Long categoryId) {
        Page<DishInfoResponseDto> page = iDishHandler.getAllDishesByRestaurantId(pageable, restaurantId, categoryId);
        DishPageResponseDto response = new DishPageResponseDto(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements()
        );
        return ResponseEntity.ok(response);
    }

}
