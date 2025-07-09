package com.project.foodCourt.infrastructure.input.rest;

import com.project.foodCourt.application.dto.request.order.OrderRequestDto;
import com.project.foodCourt.application.dto.response.order.OrderPageResponseDto;
import com.project.foodCourt.application.dto.response.order.OrderResponseDto;
import com.project.foodCourt.application.handler.IOrderHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

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

    @Test
    void getOrdersByStatus_WithStatus() {
        Page<OrderResponseDto> page = new PageImpl<>(List.of(responseDto));
        when(orderHandler.getOrdersByStatus("PENDIENTE", PageRequest.of(0, 10))).thenReturn(page);
        
        ResponseEntity<OrderPageResponseDto> result = orderRestController.getOrdersByStatus(
            PageRequest.of(0, 10), "PENDIENTE");
        
        assertNotNull(result);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(1, result.getBody().getContent().size());
    }

    @Test
    void getOrdersByStatus_WithoutStatus() {
        Page<OrderResponseDto> page = new PageImpl<>(List.of(responseDto));
        when(orderHandler.getAllOrders(PageRequest.of(0, 10))).thenReturn(page);
        
        ResponseEntity<OrderPageResponseDto> result = orderRestController.getOrdersByStatus(
            PageRequest.of(0, 10), null);
        
        assertNotNull(result);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(1, result.getBody().getContent().size());
    }

    @Test
    void assignedEmployeeIdToOrder_Success() {
        OrderResponseDto responseWithEmployee = new OrderResponseDto();
        responseWithEmployee.setNameUser("Test User");
        responseWithEmployee.setStatus("PENDIENTE");
        responseWithEmployee.setNameRestaurant("Test Restaurant");
        responseWithEmployee.setAssignedEmployee("Employee Test");
        
        when(orderHandler.assignedEmployeeIdToOrder(1L, 2L)).thenReturn(responseWithEmployee);
        
        ResponseEntity<OrderResponseDto> result = orderRestController.assignedEmployeeIdToOrder(1L, 2L);
        
        assertNotNull(result);
        assertEquals(200, result.getStatusCodeValue());
        assertNotNull(result.getBody());
        assertEquals("Employee Test", result.getBody().getAssignedEmployee());
        assertEquals("Test User", result.getBody().getNameUser());
        assertEquals("PENDIENTE", result.getBody().getStatus());
    }
}