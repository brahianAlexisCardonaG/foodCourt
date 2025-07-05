package com.project.foodCourt.infrastructure.input.rest;

import com.project.foodCourt.application.dto.request.DishRequestDto;
import com.project.foodCourt.application.dto.response.DishResponseDto;
import com.project.foodCourt.application.handler.IDishHandler;
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
class DishRestControllerTest {

    @Mock
    private IDishHandler dishHandler;

    @InjectMocks
    private DishRestController dishRestController;

    private DishRequestDto requestDto;
    private DishResponseDto responseDto;

    @BeforeEach
    void setUp() {
        requestDto = new DishRequestDto();
        requestDto.setName("Test Dish");
        requestDto.setDescription("Test Description");
        requestDto.setPrice(10.0f);
        requestDto.setRestaurantId(1L);
        requestDto.setImageUrl("http://test.com/image.png");

        responseDto = new DishResponseDto();
        responseDto.setId(1L);
        responseDto.setName("Test Dish");
        responseDto.setActive(true);
    }

    @Test
    void createDish_Success() {
        when(dishHandler.createDish(any(DishRequestDto.class))).thenReturn(responseDto);

        ResponseEntity<DishResponseDto> result = dishRestController.createDish(requestDto);

        assertNotNull(result);
        assertEquals(200, result.getStatusCodeValue());
        assertNotNull(result.getBody());
        assertEquals(1L, result.getBody().getId());
        assertEquals("Test Dish", result.getBody().getName());
        assertTrue(result.getBody().getActive());
    }

    @Test
    void createDish_HandlerCalled() {
        when(dishHandler.createDish(requestDto)).thenReturn(responseDto);

        ResponseEntity<DishResponseDto> result = dishRestController.createDish(requestDto);

        assertNotNull(result);
        assertEquals(responseDto, result.getBody());
    }
}