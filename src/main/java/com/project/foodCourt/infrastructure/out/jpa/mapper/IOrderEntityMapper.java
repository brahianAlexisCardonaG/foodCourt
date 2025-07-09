package com.project.foodCourt.infrastructure.out.jpa.mapper;

import com.project.foodCourt.domain.model.OrderModel;
import com.project.foodCourt.domain.model.modelbasic.OrderDishBasicModel;
import com.project.foodCourt.infrastructure.out.jpa.entity.OrderDishEntity;
import com.project.foodCourt.infrastructure.out.jpa.entity.OrderDishIdEntity;
import com.project.foodCourt.infrastructure.out.jpa.entity.OrderEntity;
import com.project.foodCourt.infrastructure.out.jpa.entity.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderEntityMapper {
    OrderModel toOrderModel(OrderEntity orderEntity);
    
    default OrderEntity toOrderEntity(OrderModel orderModel) {
        if (orderModel == null) {
            return null;
        }
        
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(orderModel.getId());
        orderEntity.setClientId(orderModel.getClientId());
        orderEntity.setDate(orderModel.getDate());
        orderEntity.setStatus(orderModel.getStatus());
        orderEntity.setChefId(orderModel.getChefId());
        orderEntity.setAssignedEmployeeId(orderModel.getAssignedEmployeeId());
        
        if (orderModel.getRestaurant() != null) {
            RestaurantEntity restaurant = new RestaurantEntity();
            restaurant.setId(orderModel.getRestaurant().getId());
            orderEntity.setRestaurant(restaurant);
        }
        
        if (orderModel.getOrderDishes() != null) {
            List<OrderDishEntity> orderDishEntities = orderModel.getOrderDishes().stream()
                .map(orderDish -> toOrderDishEntity(orderDish, orderEntity))
                .toList();
            orderEntity.setOrderDishes(orderDishEntities);
        }
        
        return orderEntity;
    }
    
    default OrderDishEntity toOrderDishEntity(OrderDishBasicModel orderDish, OrderEntity orderEntity) {
        if (orderDish == null) {
            return null;
        }
        
        OrderDishEntity entity = new OrderDishEntity();
        entity.setQuantity(orderDish.getQuantity());
        
        // Establecer relación con OrderEntity
        entity.setOrders(orderEntity);
        
        // Crear OrderDishIdEntity
        OrderDishIdEntity id = new OrderDishIdEntity();
        id.setOrderId(orderEntity.getId());
        id.setDishId(orderDish.getDishId());
        entity.setId(id);
        
        return entity;
    }
}