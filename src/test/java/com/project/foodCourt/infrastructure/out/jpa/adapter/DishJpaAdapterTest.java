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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DishJpaAdapterTest {

    @Mock
    private IDishRepository dishRepository;

    @Mock
    private IDishEntityMapper dishEntityMapper;

    @InjectMocks
    private DishJpaAdapter dishJpaAdapter;

    private DishModel dishModel;
    private DishEntity dishEntity;

    @BeforeEach
    void setUp() {
        dishModel = new DishModel();
        dishModel.setId(1L);
        dishModel.setName("Test Dish");
        dishModel.setPrice(10.0f);
        dishModel.setActive(true);

        dishEntity = new DishEntity();
        dishEntity.setId(1L);
        dishEntity.setName("Test Dish");
        dishEntity.setPrice("10.0");
        dishEntity.setActive(true);
    }

    @Test
    void save_Success() {
        when(dishEntityMapper.toDishEntity(dishModel)).thenReturn(dishEntity);
        when(dishRepository.save(dishEntity)).thenReturn(dishEntity);
        when(dishEntityMapper.toDishModel(dishEntity)).thenReturn(dishModel);

        DishModel result = dishJpaAdapter.save(dishModel);

        assertNotNull(result);
        assertEquals(dishModel.getId(), result.getId());
        assertEquals(dishModel.getName(), result.getName());
        verify(dishRepository).save(dishEntity);
        verify(dishEntityMapper).toDishEntity(dishModel);
        verify(dishEntityMapper).toDishModel(dishEntity);
    }

    @Test
    void save_EntityMappingCalled() {
        when(dishEntityMapper.toDishEntity(any(DishModel.class))).thenReturn(dishEntity);
        when(dishRepository.save(any(DishEntity.class))).thenReturn(dishEntity);
        when(dishEntityMapper.toDishModel(any(DishEntity.class))).thenReturn(dishModel);

        DishModel result = dishJpaAdapter.save(dishModel);

        assertNotNull(result);
        verify(dishEntityMapper).toDishEntity(dishModel);
        verify(dishEntityMapper).toDishModel(dishEntity);
    }
}