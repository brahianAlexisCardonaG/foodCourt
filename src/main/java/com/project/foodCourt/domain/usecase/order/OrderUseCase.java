package com.project.foodCourt.domain.usecase.order;

import com.project.foodCourt.domain.api.IOrderServicePort;
import com.project.foodCourt.domain.model.DishModel;
import com.project.foodCourt.domain.model.OrderModel;
import com.project.foodCourt.domain.model.RestaurantModel;
import com.project.foodCourt.domain.model.feignclient.UserRoleResponse;
import com.project.foodCourt.domain.model.modelbasic.OrderDishBasicModel;
import com.project.foodCourt.domain.model.modelbasic.RestaurantBasicModel;
import com.project.foodCourt.domain.model.orderresponse.OrderResponseModel;
import com.project.foodCourt.domain.usecase.order.util.OrderResponseBuilder;
import com.project.foodCourt.domain.spi.IDishPersistencePort;
import com.project.foodCourt.domain.spi.IOrderDishPersistencePort;
import com.project.foodCourt.domain.spi.IOrderPersistencePort;
import com.project.foodCourt.domain.spi.IRestaurantPersistencePort;
import com.project.foodCourt.domain.spi.IUserWebClientPort;
import com.project.foodCourt.domain.model.OrderDishModel;
import com.project.foodCourt.utils.ErrorCatalog;
import com.project.foodCourt.utils.GenericValidation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class OrderUseCase implements IOrderServicePort {

    private final IRestaurantPersistencePort iRestaurantPersistencePort;
    private final IDishPersistencePort iDishPersistencePort;
    private final GenericValidation genericValidation;
    private final IOrderPersistencePort iOrderPersistencePort;
    private final IUserWebClientPort iUserWebClientPort;
    private final IOrderDishPersistencePort iOrderDishPersistencePort;


    @Override
    public OrderResponseModel createOrder(OrderModel order) {
        
        //Validar que el cliente existe
        UserRoleResponse userRoleResponse = iUserWebClientPort.getUserById(order.getClientId());
        genericValidation.validateCondition(userRoleResponse == null, ErrorCatalog.USER_NOT_FOUND);
        
        //Validar que el restaurante existe
        Optional<RestaurantModel> restaurant = iRestaurantPersistencePort.getRestaurantById(order.getRestaurant().getId());
        genericValidation.validateCondition(restaurant.isEmpty(), ErrorCatalog.RESTAURANT_NOT_FOUND);
        
        //Validar que el cliente no tiene pedidos en proceso
        List<String> activeStatuses = List.of("PENDIENTE", "EN_PREPARACION", "LISTO");
        List<OrderModel> activeOrders = iOrderPersistencePort.findOrdersByClientIdAndStatuses(order.getClientId(), activeStatuses);
        genericValidation.validateCondition(!activeOrders.isEmpty(), ErrorCatalog.CLIENT_HAS_ACTIVE_ORDER);
        
        //Validar platos
        List<DishModel> dishes = validateOrderDishes(order);

        order.setStatus("PENDIENTE");
        order.setDate(LocalDate.now());
        
        //Guardar la orden
        OrderModel savedOrder = iOrderPersistencePort.save(order);
        
        //Construir OrderResponseModel usando builder
        return OrderResponseBuilder.buildOrderResponseFromOrder(
            savedOrder,
            userRoleResponse,
            restaurant.get(),
            dishes,
            order
        );
    }

    @Override
    public Page<OrderResponseModel> getOrdersByStatus(String status, Pageable pageable) {
        Page<OrderModel> orders = iOrderPersistencePort.findOrdersByStatus(status, pageable);
        
        return orders.map(order -> {
            UserRoleResponse userRoleResponse = iUserWebClientPort.getUserById(order.getClientId());
            Optional<RestaurantModel> restaurant = iRestaurantPersistencePort.getRestaurantById(order.getRestaurant().getId());
            
            List<OrderDishModel> orderDishes = iOrderDishPersistencePort.findByOrderId(order.getId());
            List<Long> dishIds = orderDishes.stream()
                .map(orderDish -> orderDish.getDishes().getId())
                .filter(id -> id != null)
                .toList();
            List<DishModel> dishes = iDishPersistencePort.findByIds(dishIds);
            
            List<OrderDishBasicModel> orderDishBasics = orderDishes.stream()
                .map(orderDish -> {
                    OrderDishBasicModel basic = new OrderDishBasicModel();
                    basic.setDishId(orderDish.getDishes().getId());
                    basic.setQuantity(orderDish.getQuantity());
                    return basic;
                })
                .toList();
            
            return OrderResponseBuilder.buildOrderResponse(
                order,
                userRoleResponse,
                restaurant.get(),
                dishes,
                orderDishBasics
            );
        });
    }

    @Override
    public Page<OrderResponseModel> getAllOrders(Pageable pageable) {
        Page<OrderModel> orders = iOrderPersistencePort.findAllOrders(pageable);
        
        return orders.map(order -> {
            UserRoleResponse userRoleResponse = iUserWebClientPort.getUserById(order.getClientId());
            Optional<RestaurantModel> restaurant = iRestaurantPersistencePort.getRestaurantById(order.getRestaurant().getId());
            
            List<OrderDishModel> orderDishes = iOrderDishPersistencePort.findByOrderId(order.getId());
            List<Long> dishIds = orderDishes.stream()
                .map(orderDish -> orderDish.getDishes().getId())
                .filter(id -> id != null)
                .toList();
            List<DishModel> dishes = iDishPersistencePort.findByIds(dishIds);
            
            List<OrderDishBasicModel> orderDishBasics = orderDishes.stream()
                .map(orderDish -> {
                    OrderDishBasicModel basic = new OrderDishBasicModel();
                    basic.setDishId(orderDish.getDishes().getId());
                    basic.setQuantity(orderDish.getQuantity());
                    return basic;
                })
                .toList();
            
            return OrderResponseBuilder.buildOrderResponse(
                order,
                userRoleResponse,
                restaurant.get(),
                dishes,
                orderDishBasics
            );
        });
    }

    @Override
    public OrderResponseModel assignedEmployeeIdToOrder(Long orderId, Long employeeId) {
        Optional<OrderModel> orderOpt = iOrderPersistencePort.findById(orderId);
        genericValidation.validateCondition(orderOpt.isEmpty(), ErrorCatalog.ORDER_NOT_FOUND);
        
        UserRoleResponse employeeResponse = iUserWebClientPort.getUserById(employeeId);
        genericValidation.validateCondition(employeeResponse == null, ErrorCatalog.USER_NOT_FOUND);
        genericValidation.validateCondition(!"EMPLOYEE".equals(employeeResponse
                .getRole().getName()), ErrorCatalog.USER_NOT_EMPLOYEE);
        
        OrderModel order = orderOpt.get();
        
        // Obtener platos antes de modificar la orden
        List<OrderDishModel> orderDishes = iOrderDishPersistencePort.findByOrderId(order.getId());
        UserRoleResponse clientResponse = iUserWebClientPort.getUserById(order.getClientId());
        Optional<RestaurantModel> restaurant = iRestaurantPersistencePort.getRestaurantById(order.getRestaurant().getId());
        
        order.setAssignedEmployeeId(employeeId);
        order.setOrderDishes(null); // Limpiar orderDishes para evitar problemas con IDs nulos
        
        OrderModel updatedOrder = iOrderPersistencePort.save(order);
        List<Long> dishIds = orderDishes.stream()
            .map(orderDish -> orderDish.getDishes().getId())
            .filter(id -> id != null)
            .toList();
        List<DishModel> dishes = iDishPersistencePort.findByIds(dishIds);
        
        List<OrderDishBasicModel> orderDishBasics = orderDishes.stream()
            .map(orderDish -> {
                OrderDishBasicModel basic = new OrderDishBasicModel();
                basic.setDishId(orderDish.getDishes().getId());
                basic.setQuantity(orderDish.getQuantity());
                return basic;
            })
            .toList();
        
        return OrderResponseBuilder.buildOrderResponseWithEmployee(
            updatedOrder,
            clientResponse,
            restaurant.get(),
            dishes,
            orderDishBasics,
            employeeResponse
        );
    }

    private List<DishModel> validateOrderDishes(OrderModel order) {
        List<Long> dishIds = order.getOrderDishes().stream()
            .map(OrderDishBasicModel::getDishId)
            .toList();
        List<DishModel> dishes = iDishPersistencePort.findByIds(dishIds);
        
        genericValidation.validateCondition(dishes.size() != dishIds.size(), ErrorCatalog.DISH_NOT_FOUND);
        
        for (DishModel dish : dishes) {
            genericValidation.validateCondition(!dish.getRestaurant().getId().equals(order.getRestaurant().getId()),
                    ErrorCatalog.DISH_NOT_FROM_RESTAURANT);
        }
        return dishes;
    }
}
