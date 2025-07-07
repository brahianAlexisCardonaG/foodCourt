package com.project.foodCourt.infrastructure.input.rest;

import com.project.foodCourt.application.dto.request.dish.DishEnableDisableRequestDto;
import com.project.foodCourt.application.dto.request.dish.DishRequestDto;
import com.project.foodCourt.application.dto.request.dish.DishUpdateRequestDto;
import com.project.foodCourt.application.dto.response.dish.DishResponseDto;
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
    private DishUpdateRequestDto updateRequestDto;
    private DishEnableDisableRequestDto enableDisableRequestDto;
    private DishResponseDto responseDto;

    @BeforeEach
    void setUp() {
        requestDto = new DishRequestDto();
        requestDto.setName("Test Dish");
        requestDto.setDescription("Test Description");
        requestDto.setPrice(10.0f);
        requestDto.setRestaurantId(1L);
        requestDto.setImageUrl("http://test.com/image.png");

        updateRequestDto = new DishUpdateRequestDto();
        updateRequestDto.setId(1L);
        updateRequestDto.setDescription("Updated Description");
        updateRequestDto.setPrice(15.0f);

        enableDisableRequestDto = new DishEnableDisableRequestDto();
        enableDisableRequestDto.setId(1L);
        enableDisableRequestDto.setUserId(1L);
        enableDisableRequestDto.setActive(false);

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

    @Test
    void updateDish_Success() {
        when(dishHandler.updateDish(any(DishUpdateRequestDto.class))).thenReturn(responseDto);

        ResponseEntity<DishResponseDto> result = dishRestController.updateDish(updateRequestDto);

        assertNotNull(result);
        assertEquals(200, result.getStatusCodeValue());
        assertNotNull(result.getBody());
        assertEquals(1L, result.getBody().getId());
    }

    @Test
    void updateDish_HandlerCalled() {
        when(dishHandler.updateDish(updateRequestDto)).thenReturn(responseDto);

        ResponseEntity<DishResponseDto> result = dishRestController.updateDish(updateRequestDto);

        assertNotNull(result);
        assertEquals(responseDto, result.getBody());
    }

    @Test
    void enableDisableDish_Success() {
        when(dishHandler.enableDisableDish(any(DishEnableDisableRequestDto.class))).thenReturn(responseDto);

        ResponseEntity<DishResponseDto> result = dishRestController.enableDisableDish(enableDisableRequestDto);

        assertNotNull(result);
        assertEquals(200, result.getStatusCodeValue());
        assertNotNull(result.getBody());
        assertEquals(1L, result.getBody().getId());
    }

    @Test
    void enableDisableDish_HandlerCalled() {
        when(dishHandler.enableDisableDish(enableDisableRequestDto)).thenReturn(responseDto);

        ResponseEntity<DishResponseDto> result = dishRestController.enableDisableDish(enableDisableRequestDto);

        assertNotNull(result);
        assertEquals(responseDto, result.getBody());
    }
}