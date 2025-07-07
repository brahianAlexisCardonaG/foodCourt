package com.project.foodCourt.infrastructure.out.jpa.adapter;

import com.project.foodCourt.domain.model.DishModel;
import com.project.foodCourt.infrastructure.out.jpa.entity.DishEntity;
import com.project.foodCourt.infrastructure.out.jpa.mapper.IDishEntityMapper;
import com.project.foodCourt.infrastructure.out.jpa.repository.IDishRepository;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DishJpaAdapterTest {

    @Mock
    private IDishRepository dishRepository;
    
    @Mock
    private IDishEntityMapper dishEntityMapper;
    
    @InjectMocks
    private DishJpaAdapter dishJpaAdapter;
    
    private DishEntity dishEntity;
    private DishModel dishModel;
    
    @BeforeEach
    void setUp() {
        dishEntity = new DishEntity();
        dishEntity.setId(1L);
        dishEntity.setName("Test Dish");
        
        dishModel = new DishModel();
        dishModel.setId(1L);
        dishModel.setName("Test Dish");
    }
    
    @Test
    void save_Success() {
        when(dishEntityMapper.toDishEntity(dishModel)).thenReturn(dishEntity);
        when(dishRepository.save(dishEntity)).thenReturn(dishEntity);
        when(dishEntityMapper.toDishModel(dishEntity)).thenReturn(dishModel);
        
        DishModel result = dishJpaAdapter.save(dishModel);
        
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Dish", result.getName());
    }
    
    @Test
    void findByName_Found() {
        when(dishRepository.findByName("Test Dish")).thenReturn(Optional.of(dishEntity));
        when(dishEntityMapper.toDishModel(dishEntity)).thenReturn(dishModel);
        
        Optional<DishModel> result = dishJpaAdapter.findByName("Test Dish");
        
        assertTrue(result.isPresent());
        assertEquals("Test Dish", result.get().getName());
    }
    
    @Test
    void findByName_NotFound() {
        when(dishRepository.findByName("Test Dish")).thenReturn(Optional.empty());
        
        Optional<DishModel> result = dishJpaAdapter.findByName("Test Dish");
        
        assertFalse(result.isPresent());
    }
    
    @Test
    void findById_Found() {
        when(dishRepository.findById(1L)).thenReturn(Optional.of(dishEntity));
        when(dishEntityMapper.toDishModel(dishEntity)).thenReturn(dishModel);
        
        Optional<DishModel> result = dishJpaAdapter.findById(1L);
        
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }
    
    @Test
    void findById_NotFound() {
        when(dishRepository.findById(1L)).thenReturn(Optional.empty());
        
        Optional<DishModel> result = dishJpaAdapter.findById(1L);
        
        assertFalse(result.isPresent());
    }
    
    @Test
    void findDishesByRestaurantId_Success() {
        Page<DishEntity> entityPage = new PageImpl<>(List.of(dishEntity));
        when(dishRepository.findByRestaurantIdAndOptionalCategoryId(PageRequest.of(0, 10), 1L, null))
            .thenReturn(entityPage);
        when(dishEntityMapper.toDishModel(dishEntity)).thenReturn(dishModel);
        
        Page<DishModel> result = dishJpaAdapter.findDishesByRestaurantId(PageRequest.of(0, 10), 1L, null);
        
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Test Dish", result.getContent().get(0).getName());
    }
}