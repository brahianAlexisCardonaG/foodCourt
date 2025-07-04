package com.project.foodCourt.infrastructure.out.jpa.adapter;

import com.project.foodCourt.domain.model.RestaurantModel;
import com.project.foodCourt.infrastructure.out.jpa.entity.RestaurantEntity;
import com.project.foodCourt.infrastructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.project.foodCourt.infrastructure.out.jpa.repository.IRestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantJpaAdapterTest {

    @Mock
    private IRestaurantRepository restaurantRepository;

    @Mock
    private IRestaurantEntityMapper restaurantEntityMapper;

    @InjectMocks
    private RestaurantJpaAdapter restaurantJpaAdapter;

    private RestaurantModel restaurantModel;
    private RestaurantEntity restaurantEntity;

    @BeforeEach
    void setUp() {
        restaurantModel = new RestaurantModel();
        restaurantModel.setId(1L);
        restaurantModel.setName("Test Restaurant");
        restaurantModel.setNit("123456789");

        restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(1L);
        restaurantEntity.setName("Test Restaurant");
        restaurantEntity.setNit("123456789");
    }

    @Test
    void save_Success() {
        when(restaurantEntityMapper.toRestaurantEntity(restaurantModel)).thenReturn(restaurantEntity);
        when(restaurantRepository.save(restaurantEntity)).thenReturn(restaurantEntity);
        when(restaurantEntityMapper.toRestaurantModel(restaurantEntity)).thenReturn(restaurantModel);

        RestaurantModel result = restaurantJpaAdapter.save(restaurantModel);

        assertNotNull(result);
        assertEquals(restaurantModel.getId(), result.getId());
        verify(restaurantRepository).save(restaurantEntity);
    }

    @Test
    void getRestaurantByName_Found() {
        when(restaurantRepository.findByName("Test Restaurant")).thenReturn(Optional.of(restaurantEntity));
        when(restaurantEntityMapper.toRestaurantModel(restaurantEntity)).thenReturn(restaurantModel);

        Optional<RestaurantModel> result = restaurantJpaAdapter.getRestaurantByName("Test Restaurant");

        assertTrue(result.isPresent());
        assertEquals("Test Restaurant", result.get().getName());
    }

    @Test
    void getRestaurantByName_NotFound() {
        when(restaurantRepository.findByName("Test Restaurant")).thenReturn(Optional.empty());

        Optional<RestaurantModel> result = restaurantJpaAdapter.getRestaurantByName("Test Restaurant");

        assertFalse(result.isPresent());
    }

    @Test
    void getRestaurantByNit_Found() {
        when(restaurantRepository.findByNit("123456789")).thenReturn(Optional.of(restaurantEntity));
        when(restaurantEntityMapper.toRestaurantModel(restaurantEntity)).thenReturn(restaurantModel);

        Optional<RestaurantModel> result = restaurantJpaAdapter.getRestaurantByNit("123456789");

        assertTrue(result.isPresent());
        assertEquals("123456789", result.get().getNit());
    }

    @Test
    void getRestaurantByNit_NotFound() {
        when(restaurantRepository.findByNit("123456789")).thenReturn(Optional.empty());

        Optional<RestaurantModel> result = restaurantJpaAdapter.getRestaurantByNit("123456789");

        assertFalse(result.isPresent());
    }
}