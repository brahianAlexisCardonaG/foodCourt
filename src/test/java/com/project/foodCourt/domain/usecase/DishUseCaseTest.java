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
        
        dishModel = new DishModel();
        dishModel.setId(1L);
        dishModel.setName("Test Dish");
        dishModel.setRestaurant(restaurantBasicModel);
        
        restaurantModel = new RestaurantModel();
        restaurantModel.setId(1L);
        restaurantModel.setName("Test Restaurant");
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
}