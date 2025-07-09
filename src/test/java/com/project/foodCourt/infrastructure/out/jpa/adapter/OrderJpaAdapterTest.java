package com.project.foodCourt.infrastructure.out.jpa.adapter;

import com.project.foodCourt.domain.model.OrderModel;
import com.project.foodCourt.infrastructure.out.jpa.entity.DishEntity;
import com.project.foodCourt.infrastructure.out.jpa.entity.OrderDishIdEntity;
import com.project.foodCourt.infrastructure.out.jpa.entity.OrderEntity;
import com.project.foodCourt.infrastructure.out.jpa.entity.OrderDishEntity;
import com.project.foodCourt.infrastructure.out.jpa.mapper.IOrderEntityMapper;
import com.project.foodCourt.infrastructure.out.jpa.repository.IOrderRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderJpaAdapterTest {

    @Mock
    private IOrderRepository orderRepository;
    
    @Mock
    private IOrderEntityMapper orderEntityMapper;
    
    @Mock
    private EntityManager entityManager;
    
    @InjectMocks
    private OrderJpaAdapter orderJpaAdapter;
    
    private OrderEntity orderEntity;
    private OrderModel orderModel;
    private OrderDishEntity orderDishEntity;
    private DishEntity dishEntity;
    
    @BeforeEach
    void setUp() {
        orderModel = new OrderModel();
        orderModel.setId(1L);
        orderModel.setClientId(1L);
        orderModel.setStatus("PENDIENTE");
        
        orderEntity = new OrderEntity();
        orderEntity.setId(1L);
        orderEntity.setClientId(1L);
        orderEntity.setStatus("PENDIENTE");
        
        dishEntity = new DishEntity();
        dishEntity.setId(1L);
        
        OrderDishIdEntity orderDishId = new OrderDishIdEntity();
        orderDishId.setOrderId(1L);
        orderDishId.setDishId(1L);
        
        orderDishEntity = new OrderDishEntity();
        orderDishEntity.setId(orderDishId);
        
        orderEntity.setOrderDishes(List.of(orderDishEntity));
    }
    
    @Test
    void save_Success() {
        when(orderEntityMapper.toOrderEntity(orderModel)).thenReturn(orderEntity);
        when(entityManager.getReference(DishEntity.class, 1L)).thenReturn(dishEntity);
        when(orderRepository.save(orderEntity)).thenReturn(orderEntity);
        when(orderEntityMapper.toOrderModel(orderEntity)).thenReturn(orderModel);
        
        OrderModel result = orderJpaAdapter.save(orderModel);
        
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("PENDIENTE", result.getStatus());
        verify(entityManager).getReference(DishEntity.class, 1L);
        verify(orderRepository).save(orderEntity);
    }
    
    @Test
    void save_WithoutOrderDishes() {
        orderEntity.setOrderDishes(null);
        when(orderEntityMapper.toOrderEntity(orderModel)).thenReturn(orderEntity);
        when(orderRepository.save(orderEntity)).thenReturn(orderEntity);
        when(orderEntityMapper.toOrderModel(orderEntity)).thenReturn(orderModel);
        
        OrderModel result = orderJpaAdapter.save(orderModel);
        
        assertNotNull(result);
        verify(orderRepository).save(orderEntity);
        verify(entityManager, never()).getReference(any(), any());
    }
    
    @Test
    void findOrdersByClientIdAndStatuses_Success() {
        List<String> statuses = List.of("PENDIENTE", "EN_PREPARACION");
        List<OrderEntity> orderEntities = List.of(orderEntity);
        
        when(orderRepository.findByClientIdAndStatusIn(1L, statuses)).thenReturn(orderEntities);
        when(orderEntityMapper.toOrderModel(orderEntity)).thenReturn(orderModel);
        
        List<OrderModel> result = orderJpaAdapter.findOrdersByClientIdAndStatuses(1L, statuses);
        
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        verify(orderRepository).findByClientIdAndStatusIn(1L, statuses);
    }
    
    @Test
    void findOrdersByClientIdAndStatuses_EmptyResult() {
        List<String> statuses = List.of("PENDIENTE");
        
        when(orderRepository.findByClientIdAndStatusIn(1L, statuses)).thenReturn(List.of());
        
        List<OrderModel> result = orderJpaAdapter.findOrdersByClientIdAndStatuses(1L, statuses);
        
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findOrdersByStatus_Success() {
        when(orderRepository.findByStatus("PENDIENTE", PageRequest.of(0, 10)))
            .thenReturn(new PageImpl<>(List.of(orderEntity)));
        when(orderEntityMapper.toOrderModel(orderEntity)).thenReturn(orderModel);
        
        Page<OrderModel> result = orderJpaAdapter.findOrdersByStatus("PENDIENTE", PageRequest.of(0, 10));
        
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("PENDIENTE", result.getContent().get(0).getStatus());
    }

    @Test
    void findAllOrders_Success() {
        when(orderRepository.findAll(PageRequest.of(0, 10)))
            .thenReturn(new PageImpl<>(List.of(orderEntity)));
        when(orderEntityMapper.toOrderModel(orderEntity)).thenReturn(orderModel);
        
        Page<OrderModel> result = orderJpaAdapter.findAllOrders(PageRequest.of(0, 10));
        
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
    }
}