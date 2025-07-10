package com.project.foodCourt.domain.usecase.order;

import com.project.foodCourt.domain.exception.BusinessException;
import com.project.foodCourt.domain.model.DishModel;
import com.project.foodCourt.domain.model.OrderDishModel;
import com.project.foodCourt.domain.model.OrderModel;
import com.project.foodCourt.domain.model.RestaurantModel;
import com.project.foodCourt.domain.model.feignclient.RoleResponse;
import com.project.foodCourt.domain.model.feignclient.UserRoleResponse;
import com.project.foodCourt.domain.model.modelbasic.DishBasicModel;
import com.project.foodCourt.domain.model.modelbasic.OrderDishBasicModel;
import com.project.foodCourt.domain.model.modelbasic.RestaurantBasicModel;
import com.project.foodCourt.domain.model.orderresponse.OrderResponseModel;
import com.project.foodCourt.domain.spi.IDishPersistencePort;
import com.project.foodCourt.domain.spi.IOrderDishPersistencePort;
import com.project.foodCourt.domain.spi.IOrderPersistencePort;
import com.project.foodCourt.domain.spi.IRestaurantPersistencePort;
import com.project.foodCourt.domain.spi.IUserWebClientPort;
import com.project.foodCourt.utils.ErrorCatalog;
import com.project.foodCourt.utils.GenericValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class OrderUseCaseTest {

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;
    
    @Mock
    private IDishPersistencePort dishPersistencePort;
    
    @Mock(lenient = true)
    private GenericValidation genericValidation;
    
    @Mock
    private IOrderPersistencePort orderPersistencePort;
    
    @Mock
    private IUserWebClientPort userWebClientPort;
    
    @Mock
    private IOrderDishPersistencePort orderDishPersistencePort;
    
    @InjectMocks
    private OrderUseCase orderUseCase;
    
    private OrderModel orderModel;
    private UserRoleResponse userRoleResponse;
    private RestaurantModel restaurantModel;
    private DishModel dishModel;
    private RestaurantBasicModel restaurantBasicModel;
    
    @BeforeEach
    void setUp() {
        restaurantBasicModel = new RestaurantBasicModel();
        restaurantBasicModel.setId(1L);
        restaurantBasicModel.setName("Test Restaurant");
        
        OrderDishBasicModel orderDish = new OrderDishBasicModel();
        orderDish.setDishId(1L);
        orderDish.setQuantity(2);
        
        orderModel = new OrderModel();
        orderModel.setClientId(1L);
        orderModel.setRestaurant(restaurantBasicModel);
        orderModel.setOrderDishes(List.of(orderDish));
        
        RoleResponse roleResponse = new RoleResponse();
        roleResponse.setName("CLIENT");
        
        userRoleResponse = new UserRoleResponse();
        userRoleResponse.setId(1L);
        userRoleResponse.setFirstName("Test User");
        userRoleResponse.setRole(roleResponse);
        
        restaurantModel = new RestaurantModel();
        restaurantModel.setId(1L);
        restaurantModel.setName("Test Restaurant");
        
        dishModel = new DishModel();
        dishModel.setId(1L);
        dishModel.setName("Test Dish");
        dishModel.setRestaurant(restaurantBasicModel);
    }
    
    @Test
    void createOrder_Success() {
        when(userWebClientPort.getUserById(1L)).thenReturn(userRoleResponse);
        when(restaurantPersistencePort.getRestaurantById(1L)).thenReturn(Optional.of(restaurantModel));
        when(orderPersistencePort.findOrdersByClientIdAndStatuses(eq(1L), anyList())).thenReturn(List.of());
        when(dishPersistencePort.findByIds(List.of(1L))).thenReturn(List.of(dishModel));
        when(orderPersistencePort.save(any(OrderModel.class))).thenReturn(orderModel);
        
        OrderResponseModel result = orderUseCase.createOrder(orderModel);
        
        assertNotNull(result);
        verify(userWebClientPort).getUserById(1L);
        verify(restaurantPersistencePort).getRestaurantById(1L);
        verify(orderPersistencePort).findOrdersByClientIdAndStatuses(eq(1L), anyList());
        verify(dishPersistencePort).findByIds(List.of(1L));
        verify(orderPersistencePort).save(any(OrderModel.class));
    }
    
    @Test
    void createOrder_UserNotFound() {
        when(userWebClientPort.getUserById(1L)).thenReturn(null);
        doThrow(new BusinessException(ErrorCatalog.USER_NOT_FOUND))
            .when(genericValidation).validateCondition(eq(true), eq(ErrorCatalog.USER_NOT_FOUND));
        
        assertThrows(BusinessException.class, () -> orderUseCase.createOrder(orderModel));
        verify(userWebClientPort).getUserById(1L);
    }
    
    @Test
    void createOrder_RestaurantNotFound() {
        when(userWebClientPort.getUserById(1L)).thenReturn(userRoleResponse);
        when(restaurantPersistencePort.getRestaurantById(1L)).thenReturn(Optional.empty());
        doThrow(new BusinessException(ErrorCatalog.RESTAURANT_NOT_FOUND))
            .when(genericValidation).validateCondition(eq(true), eq(ErrorCatalog.RESTAURANT_NOT_FOUND));
        
        assertThrows(BusinessException.class, () -> orderUseCase.createOrder(orderModel));
        verify(restaurantPersistencePort).getRestaurantById(1L);
    }
    
    @Test
    void createOrder_ClientHasActiveOrder() {
        OrderModel activeOrder = new OrderModel();
        activeOrder.setStatus("PENDIENTE");
        
        when(userWebClientPort.getUserById(1L)).thenReturn(userRoleResponse);
        when(restaurantPersistencePort.getRestaurantById(1L)).thenReturn(Optional.of(restaurantModel));
        when(orderPersistencePort.findOrdersByClientIdAndStatuses(eq(1L), anyList()))
            .thenReturn(List.of(activeOrder));
        doThrow(new BusinessException(ErrorCatalog.CLIENT_HAS_ACTIVE_ORDER))
            .when(genericValidation).validateCondition(eq(true), eq(ErrorCatalog.CLIENT_HAS_ACTIVE_ORDER));
        
        assertThrows(BusinessException.class, () -> orderUseCase.createOrder(orderModel));
    }
    
    @Test
    void createOrder_DishNotFound() {
        when(userWebClientPort.getUserById(1L)).thenReturn(userRoleResponse);
        when(restaurantPersistencePort.getRestaurantById(1L)).thenReturn(Optional.of(restaurantModel));
        when(orderPersistencePort.findOrdersByClientIdAndStatuses(eq(1L), anyList())).thenReturn(List.of());
        when(dishPersistencePort.findByIds(List.of(1L))).thenReturn(List.of());
        doThrow(new BusinessException(ErrorCatalog.DISH_NOT_FOUND))
            .when(genericValidation).validateCondition(eq(true), eq(ErrorCatalog.DISH_NOT_FOUND));
        
        assertThrows(BusinessException.class, () -> orderUseCase.createOrder(orderModel));
    }
    
    @Test
    void createOrder_DishNotFromRestaurant() {
        RestaurantBasicModel differentRestaurant = new RestaurantBasicModel();
        differentRestaurant.setId(2L);
        dishModel.setRestaurant(differentRestaurant);
        
        when(userWebClientPort.getUserById(1L)).thenReturn(userRoleResponse);
        when(restaurantPersistencePort.getRestaurantById(1L)).thenReturn(Optional.of(restaurantModel));
        when(orderPersistencePort.findOrdersByClientIdAndStatuses(eq(1L), anyList())).thenReturn(List.of());
        when(dishPersistencePort.findByIds(List.of(1L))).thenReturn(List.of(dishModel));
        doThrow(new BusinessException(ErrorCatalog.DISH_NOT_FROM_RESTAURANT))
            .when(genericValidation).validateCondition(eq(true), eq(ErrorCatalog.DISH_NOT_FROM_RESTAURANT));
        
        assertThrows(BusinessException.class, () -> orderUseCase.createOrder(orderModel));
    }

    @Test
    void getOrdersByStatus_Success() {
        OrderDishModel orderDishModel = new OrderDishModel();
        DishBasicModel dishBasic = new DishBasicModel();
        dishBasic.setId(1L);
        orderDishModel.setDishes(dishBasic);
        orderDishModel.setQuantity(2);
        
        Page<OrderModel> orderPage = new PageImpl<>(List.of(orderModel));
        when(orderPersistencePort.findOrdersByStatus("PENDIENTE", PageRequest.of(0, 10))).thenReturn(orderPage);
        when(userWebClientPort.getUserById(1L)).thenReturn(userRoleResponse);
        when(restaurantPersistencePort.getRestaurantById(1L)).thenReturn(Optional.of(restaurantModel));
        when(orderDishPersistencePort.findByOrderId(any())).thenReturn(List.of(orderDishModel));
        when(dishPersistencePort.findByIds(List.of(1L))).thenReturn(List.of(dishModel));
        
        Page<OrderResponseModel> result = orderUseCase.getOrdersByStatus("PENDIENTE", PageRequest.of(0, 10));
        
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
    }

    @Test
    void getAllOrders_Success() {
        OrderDishModel orderDishModel = new OrderDishModel();
        DishBasicModel dishBasic = new DishBasicModel();
        dishBasic.setId(1L);
        orderDishModel.setDishes(dishBasic);
        orderDishModel.setQuantity(2);
        
        Page<OrderModel> orderPage = new PageImpl<>(List.of(orderModel));
        when(orderPersistencePort.findAllOrders(PageRequest.of(0, 10))).thenReturn(orderPage);
        when(userWebClientPort.getUserById(1L)).thenReturn(userRoleResponse);
        when(restaurantPersistencePort.getRestaurantById(1L)).thenReturn(Optional.of(restaurantModel));
        when(orderDishPersistencePort.findByOrderId(any())).thenReturn(List.of(orderDishModel));
        when(dishPersistencePort.findByIds(List.of(1L))).thenReturn(List.of(dishModel));
        
        Page<OrderResponseModel> result = orderUseCase.getAllOrders(PageRequest.of(0, 10));
        
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
    }

    @Test
    void assignedEmployeeIdToOrder_Success() {
        RoleResponse employeeRole = new RoleResponse();
        employeeRole.setName("EMPLOYEE");
        
        UserRoleResponse employeeResponse = new UserRoleResponse();
        employeeResponse.setId(2L);
        employeeResponse.setFirstName("Employee");
        employeeResponse.setLastName("Test");
        employeeResponse.setRole(employeeRole);
        
        OrderDishModel orderDishModel = new OrderDishModel();
        DishBasicModel dishBasic = new DishBasicModel();
        dishBasic.setId(1L);
        orderDishModel.setDishes(dishBasic);
        orderDishModel.setQuantity(2);
        
        orderModel.setId(1L);
        
        when(orderPersistencePort.findById(1L)).thenReturn(Optional.of(orderModel));
        when(userWebClientPort.getUserById(2L)).thenReturn(employeeResponse);
        when(userWebClientPort.getUserById(1L)).thenReturn(userRoleResponse);
        when(restaurantPersistencePort.getRestaurantById(1L)).thenReturn(Optional.of(restaurantModel));
        when(orderDishPersistencePort.findByOrderId(1L)).thenReturn(List.of(orderDishModel));
        when(dishPersistencePort.findByIds(List.of(1L))).thenReturn(List.of(dishModel));
        when(orderPersistencePort.save(any(OrderModel.class))).thenReturn(orderModel);
        
        OrderResponseModel result = orderUseCase.assignedEmployeeIdToOrder(1L, 2L);
        
        assertNotNull(result);
        assertEquals("Employee Test", result.getAssignedEmployee());
        verify(orderPersistencePort).save(any(OrderModel.class));
    }
    
    @Test
    void assignedEmployeeIdToOrder_OrderNotFound() {
        when(orderPersistencePort.findById(1L)).thenReturn(Optional.empty());
        doThrow(new BusinessException(ErrorCatalog.ORDER_NOT_FOUND))
            .when(genericValidation).validateCondition(eq(true), eq(ErrorCatalog.ORDER_NOT_FOUND));
        
        assertThrows(BusinessException.class, () -> orderUseCase.assignedEmployeeIdToOrder(1L, 2L));
        verify(orderPersistencePort).findById(1L);
    }
    
    @Test
    void assignedEmployeeIdToOrder_EmployeeNotFound() {
        orderModel.setId(1L);
        
        when(orderPersistencePort.findById(1L)).thenReturn(Optional.of(orderModel));
        when(userWebClientPort.getUserById(2L)).thenReturn(null);
        doThrow(new BusinessException(ErrorCatalog.USER_NOT_FOUND))
            .when(genericValidation).validateCondition(eq(true), eq(ErrorCatalog.USER_NOT_FOUND));
        
        assertThrows(BusinessException.class, () -> orderUseCase.assignedEmployeeIdToOrder(1L, 2L));
        verify(userWebClientPort).getUserById(2L);
    }
    
    @Test
    void assignedEmployeeIdToOrder_UserNotEmployee() {
        RoleResponse clientRole = new RoleResponse();
        clientRole.setName("CLIENT");
        
        UserRoleResponse nonEmployeeResponse = new UserRoleResponse();
        nonEmployeeResponse.setId(2L);
        nonEmployeeResponse.setRole(clientRole);
        
        orderModel.setId(1L);
        
        when(orderPersistencePort.findById(1L)).thenReturn(Optional.of(orderModel));
        when(userWebClientPort.getUserById(2L)).thenReturn(nonEmployeeResponse);
        doThrow(new BusinessException(ErrorCatalog.USER_NOT_EMPLOYEE))
            .when(genericValidation).validateCondition(eq(true), eq(ErrorCatalog.USER_NOT_EMPLOYEE));
        
        assertThrows(BusinessException.class, () -> orderUseCase.assignedEmployeeIdToOrder(1L, 2L));
    }
    
    @Test
    void updateStatusOrderToReady_Success() {
        RoleResponse employeeRole = new RoleResponse();
        employeeRole.setName("EMPLOYEE");
        
        UserRoleResponse employeeResponse = new UserRoleResponse();
        employeeResponse.setId(2L);
        employeeResponse.setFirstName("Employee");
        employeeResponse.setRole(employeeRole);
        
        orderModel.setId(1L);
        orderModel.setStatus("EN_PREPARACION");
        
        when(orderPersistencePort.findById(1L)).thenReturn(Optional.of(orderModel));
        when(userWebClientPort.getUserById(2L)).thenReturn(employeeResponse);
        when(orderPersistencePort.save(any(OrderModel.class))).thenReturn(orderModel);
        
        OrderModel result = orderUseCase.updateStatusOrderToReady(1L, 2L);
        
        assertNotNull(result);
        verify(orderPersistencePort).findById(1L);
        verify(userWebClientPort).getUserById(2L);
        verify(orderPersistencePort).save(any(OrderModel.class));
    }
    
    @Test
    void updateStatusOrderToReady_OrderNotFound() {
        when(orderPersistencePort.findById(1L)).thenReturn(Optional.empty());
        doThrow(new BusinessException(ErrorCatalog.ORDER_NOT_FOUND))
            .when(genericValidation).validateCondition(eq(true), eq(ErrorCatalog.ORDER_NOT_FOUND));
        
        assertThrows(BusinessException.class, () -> orderUseCase.updateStatusOrderToReady(1L, 2L));
        verify(orderPersistencePort).findById(1L);
    }
    
    @Test
    void updateStatusOrderToReady_EmployeeNotFound() {
        orderModel.setId(1L);
        
        when(orderPersistencePort.findById(1L)).thenReturn(Optional.of(orderModel));
        when(userWebClientPort.getUserById(2L)).thenReturn(null);
        doThrow(new BusinessException(ErrorCatalog.USER_NOT_FOUND))
            .when(genericValidation).validateCondition(eq(true), eq(ErrorCatalog.USER_NOT_FOUND));
        
        assertThrows(BusinessException.class, () -> orderUseCase.updateStatusOrderToReady(1L, 2L));
        verify(userWebClientPort).getUserById(2L);
    }
    
    @Test
    void updateStatusOrderToReady_UserNotEmployee() {
        RoleResponse clientRole = new RoleResponse();
        clientRole.setName("CLIENT");
        
        UserRoleResponse nonEmployeeResponse = new UserRoleResponse();
        nonEmployeeResponse.setId(2L);
        nonEmployeeResponse.setRole(clientRole);
        
        orderModel.setId(1L);
        
        when(orderPersistencePort.findById(1L)).thenReturn(Optional.of(orderModel));
        when(userWebClientPort.getUserById(2L)).thenReturn(nonEmployeeResponse);
        doThrow(new BusinessException(ErrorCatalog.USER_NOT_EMPLOYEE))
            .when(genericValidation).validateCondition(eq(true), eq(ErrorCatalog.USER_NOT_EMPLOYEE));
        
        assertThrows(BusinessException.class, () -> orderUseCase.updateStatusOrderToReady(1L, 2L));
    }
}