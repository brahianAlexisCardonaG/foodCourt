package com.project.foodCourt.domain.usecase;

import com.project.foodCourt.domain.exception.BusinessException;
import com.project.foodCourt.domain.model.RestaurantModel;
import com.project.foodCourt.domain.model.feignclient.RoleResponse;
import com.project.foodCourt.domain.model.feignclient.UserRoleResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantUseCaseTest {

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;
    
    @Mock
    private IUserWebClientPort userWebClientPort;
    
    @Mock(lenient = true)
    private GenericValidation genericValidation;
    
    @InjectMocks
    private RestaurantUseCase restaurantUseCase;
    
    private RestaurantModel restaurantModel;
    private UserRoleResponse userRoleResponse;
    
    @BeforeEach
    void setUp() {
        restaurantModel = new RestaurantModel();
        restaurantModel.setOwnerId(1L);
        restaurantModel.setName("Test Restaurant");
        restaurantModel.setNit("123456789");
        
        RoleResponse roleResponse = new RoleResponse();
        roleResponse.setName("OWNER");
        
        userRoleResponse = new UserRoleResponse();
        userRoleResponse.setRole(roleResponse);
    }
    
    @Test
    void createRestaurant_Success() {
        when(userWebClientPort.getUserById(1L)).thenReturn(userRoleResponse);
        when(restaurantPersistencePort.getRestaurantByName("Test Restaurant")).thenReturn(Optional.empty());
        when(restaurantPersistencePort.getRestaurantByNit("123456789")).thenReturn(Optional.empty());
        when(restaurantPersistencePort.save(restaurantModel)).thenReturn(restaurantModel);
        
        RestaurantModel result = restaurantUseCase.createRestaurant(restaurantModel);
        
        assertNotNull(result);
        verify(genericValidation, times(4)).validateCondition(any(Boolean.class), any(ErrorCatalog.class));
        verify(restaurantPersistencePort).save(restaurantModel);
    }
    
    @Test
    void createRestaurant_UserNotFound() {
        when(userWebClientPort.getUserById(1L)).thenReturn(null);
        doThrow(new BusinessException(ErrorCatalog.USER_NOT_FOUND))
            .when(genericValidation).validateCondition(anyBoolean(), eq(ErrorCatalog.USER_NOT_FOUND));
        
        assertThrows(BusinessException.class, () -> restaurantUseCase.createRestaurant(restaurantModel));
    }
    
    @Test
    void createRestaurant_UserNotOwner() {
        userRoleResponse.getRole().setName("USER");
        when(userWebClientPort.getUserById(1L)).thenReturn(userRoleResponse);
        
        doThrow(new BusinessException(ErrorCatalog.USER_NOT_OWNER))
            .when(genericValidation).validateCondition(anyBoolean(), eq(ErrorCatalog.USER_NOT_OWNER));
        
        assertThrows(BusinessException.class, () -> restaurantUseCase.createRestaurant(restaurantModel));
    }
    
    @Test
    void createRestaurant_RestaurantAlreadyExistsByName() {
        when(userWebClientPort.getUserById(1L)).thenReturn(userRoleResponse);
        when(restaurantPersistencePort.getRestaurantByName("Test Restaurant")).thenReturn(Optional.of(restaurantModel));
        doThrow(new BusinessException(ErrorCatalog.RESTAURANT_ALREADY_EXISTS))
            .when(genericValidation).validateCondition(anyBoolean(), eq(ErrorCatalog.RESTAURANT_ALREADY_EXISTS));
        
        assertThrows(BusinessException.class, () -> restaurantUseCase.createRestaurant(restaurantModel));
    }
    
    @Test
    void createRestaurant_RestaurantAlreadyExistsByNit() {
        when(userWebClientPort.getUserById(1L)).thenReturn(userRoleResponse);
        when(restaurantPersistencePort.getRestaurantByName("Test Restaurant")).thenReturn(Optional.empty());
        when(restaurantPersistencePort.getRestaurantByNit("123456789")).thenReturn(Optional.of(restaurantModel));
        doThrow(new BusinessException(ErrorCatalog.RESTAURANT_ALREADY_EXISTS))
            .when(genericValidation).validateCondition(eq(true), eq(ErrorCatalog.RESTAURANT_ALREADY_EXISTS));
        
        assertThrows(BusinessException.class, () -> restaurantUseCase.createRestaurant(restaurantModel));
    }
    
    @Test
    void getAllRestaurants_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        Pageable sortedPageable = PageRequest.of(0, 10, Sort.by("name").ascending());
        Page<RestaurantModel> expectedPage = new PageImpl<>(List.of(restaurantModel));
        
        when(restaurantPersistencePort.getAllRestaurants(sortedPageable)).thenReturn(expectedPage);
        
        Page<RestaurantModel> result = restaurantUseCase.getAllRestaurants(pageable);
        
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(restaurantPersistencePort).getAllRestaurants(sortedPageable);
    }
}