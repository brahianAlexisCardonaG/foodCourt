package com.project.foodCourt.infrastructure.input.rest;

import com.project.foodCourt.application.dto.request.restaurant.RestaurantRequestDto;
import com.project.foodCourt.application.dto.response.restaurant.RestaurantInfoResponseDto;
import com.project.foodCourt.application.dto.response.restaurant.RestaurantPageResponseDto;
import com.project.foodCourt.application.dto.response.restaurant.RestaurantResponseDto;
import com.project.foodCourt.application.handler.IRestaurantHandler;
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
class RestaurantRestControllerTest {

    @Mock
    private IRestaurantHandler restaurantHandler;

    @InjectMocks
    private RestaurantRestController restaurantRestController;

    private RestaurantRequestDto requestDto;
    private RestaurantResponseDto responseDto;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        requestDto = new RestaurantRequestDto();
        requestDto.setName("Test Restaurant");
        requestDto.setAddress("Test Address");
        requestDto.setOwnerId(1L);
        requestDto.setPhone("+1234567890");
        requestDto.setLogoUrl("http://test.com/logo.png");
        requestDto.setNit("123456789");

        responseDto = new RestaurantResponseDto();
        responseDto.setId(1L);
        responseDto.setName("Test Restaurant");
    }

    @Test
    void createRestaurant_Success() {
        when(restaurantHandler.createRestaurant(any(RestaurantRequestDto.class))).thenReturn(responseDto);

        ResponseEntity<RestaurantResponseDto> result = restaurantRestController.createRestaurant(requestDto);

        assertNotNull(result);
        assertEquals(200, result.getStatusCodeValue());
        assertNotNull(result.getBody());
        assertEquals(1L, result.getBody().getId());
        assertEquals("Test Restaurant", result.getBody().getName());
    }

    @Test
    void createRestaurant_HandlerCalled() {
        when(restaurantHandler.createRestaurant(requestDto)).thenReturn(responseDto);

        ResponseEntity<RestaurantResponseDto> result = restaurantRestController.createRestaurant(requestDto);

        assertNotNull(result);
        assertEquals(responseDto, result.getBody());
    }

    @Test
    void getAllRestaurants_Success() {
        RestaurantInfoResponseDto infoDto = new RestaurantInfoResponseDto();
        infoDto.setName("Test Restaurant");
        
        Page<RestaurantInfoResponseDto> page = new PageImpl<>(List.of(infoDto), PageRequest.of(0, 10), 1);
        when(restaurantHandler.getAllRestaurants(any(Pageable.class))).thenReturn(page);
        
        ResponseEntity<RestaurantPageResponseDto> result = restaurantRestController.getAllRestaurants(PageRequest.of(0, 10));
        
        assertNotNull(result);
        assertEquals(200, result.getStatusCodeValue());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().getContent().size());
        assertEquals(1L, result.getBody().getTotalElements());
    }
}