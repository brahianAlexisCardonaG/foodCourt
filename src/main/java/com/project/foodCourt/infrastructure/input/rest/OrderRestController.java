package com.project.foodCourt.infrastructure.input.rest;

import com.project.foodCourt.application.dto.request.dish.DishEnableDisableRequestDto;
import com.project.foodCourt.application.dto.request.dish.DishRequestDto;
import com.project.foodCourt.application.dto.request.order.OrderRequestDto;
import com.project.foodCourt.application.dto.response.dish.DishInfoResponseDto;
import com.project.foodCourt.application.dto.response.dish.DishPageResponseDto;
import com.project.foodCourt.application.dto.response.dish.DishResponseDto;
import com.project.foodCourt.application.dto.response.order.OrderPageResponseDto;
import com.project.foodCourt.application.dto.response.order.OrderResponseDto;
import com.project.foodCourt.application.handler.IOrderHandler;
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
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderRestController {
    private final IOrderHandler iOrderHandler;

    @Operation(summary = "Add a new Order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "order created", content = @Content),
    })
    @PostMapping("/create-order-client")
    public ResponseEntity<OrderResponseDto> createOrder(
            @Valid @RequestBody OrderRequestDto orderRequestDto
    ) {
        return ResponseEntity.ok(iOrderHandler.createOrder(orderRequestDto));
    }

    @Operation(summary = "Get List Of orders by status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get List Of orders by status Found", content = @Content)
    })
    @GetMapping("/employee-orders")
    public ResponseEntity<OrderPageResponseDto> getOrdersByStatus(Pageable pageable,
                                                                  @RequestParam(required = false) String status) {
        Page<OrderResponseDto> page = status != null ?
                iOrderHandler.getOrdersByStatus(status, pageable) :
                iOrderHandler.getAllOrders(pageable);
        OrderPageResponseDto response = new OrderPageResponseDto(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements()
        );
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "assigned Employee to order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "assigned Employee to order with success", content = @Content),
    })
    @PatchMapping("/assigned-employee")
    public ResponseEntity<OrderResponseDto> assignedEmployeeIdToOrder(
            @RequestParam Long orderId,
            @RequestParam Long employeeId
    ) {
        return ResponseEntity.ok(iOrderHandler.assignedEmployeeIdToOrder(orderId, employeeId));
    }
}