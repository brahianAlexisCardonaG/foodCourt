package com.project.foodCourt.infrastructure.input.rest;

import com.project.foodCourt.application.dto.request.order.OrderRequestDto;
import com.project.foodCourt.application.dto.response.order.OrderResponseDto;
import com.project.foodCourt.application.handler.IOrderHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderRestControllerTest {

    @Mock
    private IOrderHandler orderHandler;

    @InjectMocks
    private OrderRestController orderRestController;

    private OrderRequestDto requestDto;
    private OrderResponseDto responseDto;

    @BeforeEach
    void setUp() {
        requestDto = new OrderRequestDto();
        requestDto.setClientId(1L);
        requestDto.setRestaurantId(1L);

        responseDto = new OrderResponseDto();
        responseDto.setNameUser("Test User");
        responseDto.setStatus("PENDIENTE");
        responseDto.setNameRestaurant("Test Restaurant");
    }

    @Test
    void createOrder_Success() {
        when(orderHandler.createOrder(any(OrderRequestDto.class))).thenReturn(responseDto);

        ResponseEntity<OrderResponseDto> result = orderRestController.createOrder(requestDto);

        assertNotNull(result);
        assertEquals(200, result.getStatusCodeValue());
        assertNotNull(result.getBody());
        assertEquals("Test User", result.getBody().getNameUser());
        assertEquals("PENDIENTE", result.getBody().getStatus());
        assertEquals("Test Restaurant", result.getBody().getNameRestaurant());
    }
}