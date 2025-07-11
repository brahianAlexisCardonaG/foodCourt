package com.project.foodCourt.infrastructure.out.jpa.adapter;

import com.project.foodCourt.domain.model.OrderModel;
import com.project.foodCourt.infrastructure.out.jpa.entity.DishEntity;
import com.project.foodCourt.infrastructure.out.jpa.entity.OrderDishEntity;
import com.project.foodCourt.infrastructure.out.jpa.entity.OrderDishIdEntity;
import com.project.foodCourt.infrastructure.out.jpa.entity.OrderEntity;
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
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
    
    private OrderModel orderModel;
    private OrderEntity orderEntity;
    
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
    }
    
    @Test
    void save_Success() {
        when(orderEntityMapper.toOrderEntity(orderModel)).thenReturn(orderEntity);
        when(orderRepository.save(orderEntity)).thenReturn(orderEntity);
        when(orderEntityMapper.toOrderModel(orderEntity)).thenReturn(orderModel);
        
        OrderModel result = orderJpaAdapter.save(orderModel);
        
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("PENDIENTE", result.getStatus());
        verify(orderEntityMapper).toOrderEntity(orderModel);
        verify(orderRepository).save(orderEntity);
        verify(orderEntityMapper).toOrderModel(orderEntity);
    }
    
    @Test
    void findOrdersByClientIdAndStatuses_Success() {
        List<String> statuses = List.of("PENDIENTE", "EN_PREPARACION");
        List<OrderEntity> entities = List.of(orderEntity);
        List<OrderModel> models = List.of(orderModel);
        
        when(orderRepository.findByClientIdAndStatusIn(1L, statuses)).thenReturn(entities);
        when(orderEntityMapper.toOrderModel(orderEntity)).thenReturn(orderModel);
        
        List<OrderModel> result = orderJpaAdapter.findOrdersByClientIdAndStatuses(1L, statuses);
        
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        verify(orderRepository).findByClientIdAndStatusIn(1L, statuses);
    }
    
    @Test
    void findOrdersByStatus_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<OrderEntity> entityPage = new PageImpl<>(List.of(orderEntity));
        
        when(orderRepository.findByStatus("PENDIENTE", pageable)).thenReturn(entityPage);
        when(orderEntityMapper.toOrderModel(orderEntity)).thenReturn(orderModel);
        
        Page<OrderModel> result = orderJpaAdapter.findOrdersByStatus("PENDIENTE", pageable);
        
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(orderRepository).findByStatus("PENDIENTE", pageable);
    }
    
    @Test
    void findAllOrders_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<OrderEntity> entityPage = new PageImpl<>(List.of(orderEntity));
        
        when(orderRepository.findAll(pageable)).thenReturn(entityPage);
        when(orderEntityMapper.toOrderModel(orderEntity)).thenReturn(orderModel);
        
        Page<OrderModel> result = orderJpaAdapter.findAllOrders(pageable);
        
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(orderRepository).findAll(pageable);
    }
    
    @Test
    void findById_Success() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(orderEntity));
        when(orderEntityMapper.toOrderModel(orderEntity)).thenReturn(orderModel);
        
        Optional<OrderModel> result = orderJpaAdapter.findById(1L);
        
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(orderRepository).findById(1L);
        verify(orderEntityMapper).toOrderModel(orderEntity);
    }
    
    @Test
    void findById_NotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());
        
        Optional<OrderModel> result = orderJpaAdapter.findById(1L);
        
        assertFalse(result.isPresent());
        verify(orderRepository).findById(1L);
        verify(orderEntityMapper, never()).toOrderModel(any());
    }
    
    @Test
    void save_WithNullOrderDishes() {
        orderEntity.setOrderDishes(null);
        
        when(orderEntityMapper.toOrderEntity(orderModel)).thenReturn(orderEntity);
        when(orderRepository.save(orderEntity)).thenReturn(orderEntity);
        when(orderEntityMapper.toOrderModel(orderEntity)).thenReturn(orderModel);
        
        OrderModel result = orderJpaAdapter.save(orderModel);
        
        assertNotNull(result);
        verify(orderRepository).save(orderEntity);
    }
    
    @Test
    void findOrdersByClientIdAndStatuses_EmptyResult() {
        List<String> statuses = List.of("PENDIENTE");
        
        when(orderRepository.findByClientIdAndStatusIn(1L, statuses)).thenReturn(List.of());
        
        List<OrderModel> result = orderJpaAdapter.findOrdersByClientIdAndStatuses(1L, statuses);
        
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(orderRepository).findByClientIdAndStatusIn(1L, statuses);
    }
    
    @Test
    void save_WithOrderDishes() {
        OrderDishIdEntity orderDishId = new OrderDishIdEntity();
        orderDishId.setDishId(1L);
        
        OrderDishEntity orderDish = new OrderDishEntity();
        orderDish.setId(orderDishId);
        orderDish.setQuantity(2);
        
        orderEntity.setOrderDishes(List.of(orderDish));
        
        DishEntity dishReference = new DishEntity();
        dishReference.setId(1L);
        
        when(orderEntityMapper.toOrderEntity(orderModel)).thenReturn(orderEntity);
        when(entityManager.getReference(DishEntity.class, 1L)).thenReturn(dishReference);
        when(orderRepository.save(orderEntity)).thenReturn(orderEntity);
        when(orderEntityMapper.toOrderModel(orderEntity)).thenReturn(orderModel);
        
        OrderModel result = orderJpaAdapter.save(orderModel);
        
        assertNotNull(result);
        verify(entityManager).getReference(DishEntity.class, 1L);
        verify(orderRepository).save(orderEntity);
        assertEquals(dishReference, orderDish.getDishes());
    }
}