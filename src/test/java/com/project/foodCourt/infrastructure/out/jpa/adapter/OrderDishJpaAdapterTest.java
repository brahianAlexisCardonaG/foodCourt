package com.project.foodCourt.infrastructure.out.jpa.adapter;

import com.project.foodCourt.domain.model.OrderDishModel;
import com.project.foodCourt.infrastructure.out.jpa.entity.OrderDishEntity;
import com.project.foodCourt.infrastructure.out.jpa.mapper.IOrderDishEntityMapper;
import com.project.foodCourt.infrastructure.out.jpa.repository.IOrderDishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderDishJpaAdapterTest {

    @Mock
    private IOrderDishRepository orderDishRepository;
    
    @Mock
    private IOrderDishEntityMapper orderDishEntityMapper;
    
    @InjectMocks
    private OrderDishJpaAdapter orderDishJpaAdapter;
    
    private OrderDishEntity orderDishEntity;
    private OrderDishModel orderDishModel;
    
    @BeforeEach
    void setUp() {
        orderDishEntity = new OrderDishEntity();
        orderDishEntity.setQuantity(2);
        
        orderDishModel = new OrderDishModel();
        orderDishModel.setQuantity(2);
    }
    
    @Test
    void findByOrderId_Success() {
        when(orderDishRepository.findByIdOrderId(1L)).thenReturn(List.of(orderDishEntity));
        when(orderDishEntityMapper.toOrderDishModel(orderDishEntity)).thenReturn(orderDishModel);
        
        List<OrderDishModel> result = orderDishJpaAdapter.findByOrderId(1L);
        
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(2, result.get(0).getQuantity());
    }
    
    @Test
    void findByOrderId_EmptyResult() {
        when(orderDishRepository.findByIdOrderId(1L)).thenReturn(List.of());
        
        List<OrderDishModel> result = orderDishJpaAdapter.findByOrderId(1L);
        
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}