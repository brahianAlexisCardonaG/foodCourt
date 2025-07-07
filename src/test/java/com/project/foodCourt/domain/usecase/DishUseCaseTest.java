package com.project.foodCourt.domain.usecase;

import com.project.foodCourt.domain.exception.BusinessException;
import com.project.foodCourt.domain.model.DishModel;
import com.project.foodCourt.domain.model.RestaurantModel;
import com.project.foodCourt.domain.model.modelbasic.RestaurantBasicModel;
import com.project.foodCourt.domain.model.modelbasic.mapper.IRestaurantBasicModelMapper;
import com.project.foodCourt.domain.spi.IDishPersistencePort;
import com.project.foodCourt.domain.spi.IRestaurantPersistencePort;
import com.project.foodCourt.utils.ErrorCatalog;
import com.project.foodCourt.utils.GenericValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DishUseCaseTest {

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;
    
    @Mock
    private IDishPersistencePort dishPersistencePort;
    
    @Mock(lenient = true)
    private GenericValidation genericValidation;
    
    @Mock
    private IRestaurantBasicModelMapper restaurantBasicModelMapper;
    
    @InjectMocks
    private DishUseCase dishUseCase;
    
    private DishModel dishModel;
    private RestaurantModel restaurantModel;
    private RestaurantBasicModel restaurantBasicModel;
    
    @BeforeEach
    void setUp() {
        restaurantBasicModel = new RestaurantBasicModel();
        restaurantBasicModel.setId(1L);
        restaurantBasicModel.setName("Test Restaurant");
        restaurantBasicModel.setOwnerId(1L);
        
        dishModel = new DishModel();
        dishModel.setId(1L);
        dishModel.setName("Test Dish");
        dishModel.setRestaurant(restaurantBasicModel);
        
        restaurantModel = new RestaurantModel();
        restaurantModel.setId(1L);
        restaurantModel.setName("Test Restaurant");
        restaurantModel.setOwnerId(1L);
    }
    
    @Test
    void createDish_Success() {
        when(restaurantPersistencePort.getRestaurantById(1L)).thenReturn(Optional.of(restaurantModel));
        when(restaurantBasicModelMapper.toRestaurantBasicModel(restaurantModel)).thenReturn(restaurantBasicModel);
        when(dishPersistencePort.save(dishModel)).thenReturn(dishModel);
        
        DishModel result = dishUseCase.createDish(dishModel);
        
        assertNotNull(result);
        assertTrue(result.getActive());
        assertEquals(restaurantBasicModel, result.getRestaurant());
        verify(dishPersistencePort).save(dishModel);
    }
    
    @Test
    void createDish_RestaurantNotFound() {
        when(restaurantPersistencePort.getRestaurantById(1L)).thenReturn(Optional.empty());
        doThrow(new BusinessException(ErrorCatalog.RESTAURANT_NOT_FOUND))
            .when(genericValidation).validateCondition(anyBoolean(), eq(ErrorCatalog.RESTAURANT_NOT_FOUND));
        
        assertThrows(BusinessException.class, () -> dishUseCase.createDish(dishModel));
        verify(restaurantPersistencePort).getRestaurantById(1L);
        verify(dishPersistencePort, never()).save(any());
    }

    @Test
    void updateDish_Success() {
        DishModel existingDish = new DishModel();
        existingDish.setId(1L);
        existingDish.setName("Existing Dish");
        existingDish.setPrice(5.0f);
        existingDish.setDescription("Old Description");
        
        when(dishPersistencePort.findById(1L)).thenReturn(Optional.of(existingDish));
        when(dishPersistencePort.save(any(DishModel.class))).thenReturn(existingDish);
        
        DishModel updateData = new DishModel();
        updateData.setId(1L);
        updateData.setPrice(15.0f);
        updateData.setDescription("New Description");
        
        DishModel result = dishUseCase.updateDish(updateData);
        
        assertNotNull(result);
        assertEquals(15.0f, existingDish.getPrice());
        assertEquals("New Description", existingDish.getDescription());
        verify(dishPersistencePort).findById(1L);
        verify(dishPersistencePort).save(existingDish);
    }

    @Test
    void updateDish_DishNotFound() {
        when(dishPersistencePort.findById(1L)).thenReturn(Optional.empty());
        doThrow(new BusinessException(ErrorCatalog.DISH_NOT_FOUND))
            .when(genericValidation).validateCondition(anyBoolean(), eq(ErrorCatalog.DISH_NOT_FOUND));
        
        assertThrows(BusinessException.class, () -> dishUseCase.updateDish(dishModel));
        verify(dishPersistencePort).findById(1L);
        verify(dishPersistencePort, never()).save(any());
    }

    @Test
    void disableEnableDish_Success() {
        DishModel existingDish = new DishModel();
        existingDish.setId(1L);
        existingDish.setRestaurant(restaurantBasicModel);
        existingDish.setActive(true);
        
        DishModel requestDish = new DishModel();
        requestDish.setId(1L);
        requestDish.setRestaurant(restaurantBasicModel);
        requestDish.setActive(false);
        
        when(dishPersistencePort.findById(1L)).thenReturn(Optional.of(existingDish));
        when(restaurantPersistencePort.getRestaurantById(1L)).thenReturn(Optional.of(restaurantModel));
        
        DishModel result = dishUseCase.disableEnableDish(requestDish);
        
        assertNotNull(result);
        assertFalse(result.getActive());
        verify(dishPersistencePort).findById(1L);
        verify(restaurantPersistencePort).getRestaurantById(1L);
    }

    @Test
    void disableEnableDish_DishNotFound() {
        when(dishPersistencePort.findById(1L)).thenReturn(Optional.empty());
        doThrow(new BusinessException(ErrorCatalog.DISH_NOT_FOUND))
            .when(genericValidation).validateCondition(anyBoolean(), eq(ErrorCatalog.DISH_NOT_FOUND));
        
        assertThrows(BusinessException.class, () -> dishUseCase.disableEnableDish(dishModel));
        verify(dishPersistencePort).findById(1L);
    }

    @Test
    void disableEnableDish_RestaurantNotFound() {
        DishModel existingDish = new DishModel();
        existingDish.setId(1L);
        existingDish.setRestaurant(restaurantBasicModel);
        
        when(dishPersistencePort.findById(1L)).thenReturn(Optional.of(existingDish));
        when(restaurantPersistencePort.getRestaurantById(1L)).thenReturn(Optional.empty());
        doThrow(new BusinessException(ErrorCatalog.RESTAURANT_NOT_FOUND))
            .when(genericValidation).validateCondition(anyBoolean(), eq(ErrorCatalog.RESTAURANT_NOT_FOUND));
        
        assertThrows(BusinessException.class, () -> dishUseCase.disableEnableDish(dishModel));
        verify(dishPersistencePort).findById(1L);
        verify(restaurantPersistencePort).getRestaurantById(1L);
    }

    @Test
    void disableEnableDish_NotOwner() {
        DishModel existingDish = new DishModel();
        existingDish.setId(1L);
        existingDish.setRestaurant(restaurantBasicModel);
        
        RestaurantBasicModel differentOwnerRestaurant = new RestaurantBasicModel();
        differentOwnerRestaurant.setId(1L);
        differentOwnerRestaurant.setOwnerId(2L);
        
        DishModel requestDish = new DishModel();
        requestDish.setId(1L);
        requestDish.setRestaurant(differentOwnerRestaurant);
        
        when(dishPersistencePort.findById(1L)).thenReturn(Optional.of(existingDish));
        when(restaurantPersistencePort.getRestaurantById(1L)).thenReturn(Optional.of(restaurantModel));
        doThrow(new BusinessException(ErrorCatalog.RESTAURANT_NOT_OWNER))
            .when(genericValidation).validateCondition(anyBoolean(), eq(ErrorCatalog.RESTAURANT_NOT_OWNER));
        
        assertThrows(BusinessException.class, () -> dishUseCase.disableEnableDish(requestDish));
        verify(dishPersistencePort).findById(1L);
        verify(restaurantPersistencePort).getRestaurantById(1L);
    }
}