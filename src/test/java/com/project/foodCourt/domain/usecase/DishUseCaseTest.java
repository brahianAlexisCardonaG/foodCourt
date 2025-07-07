package com.project.foodCourt.domain.usecase;

import com.project.foodCourt.domain.exception.BusinessException;
import com.project.foodCourt.domain.model.CategoryModel;
import com.project.foodCourt.domain.model.DishModel;
import com.project.foodCourt.domain.model.RestaurantModel;
import com.project.foodCourt.domain.model.modelbasic.CategoryBasicModel;
import com.project.foodCourt.domain.model.modelbasic.RestaurantBasicModel;
import com.project.foodCourt.domain.model.modelbasic.mapper.ICategoryBasicModelMapper;
import com.project.foodCourt.domain.model.modelbasic.mapper.IRestaurantBasicModelMapper;
import com.project.foodCourt.domain.spi.ICategoryPersistencePort;
import com.project.foodCourt.domain.spi.IDishPersistencePort;
import com.project.foodCourt.domain.spi.IRestaurantPersistencePort;
import com.project.foodCourt.utils.ErrorCatalog;
import com.project.foodCourt.utils.GenericValidation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;
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
    
    @Mock
    private ICategoryPersistencePort categoryPersistencePort;
    
    @Mock
    private ICategoryBasicModelMapper categoryBasicModelMapper;
    
    @InjectMocks
    private DishUseCase dishUseCase;
    
    private DishModel dishModel;
    private RestaurantModel restaurantModel;
    private RestaurantBasicModel restaurantBasicModel;
    private CategoryModel categoryModel;
    private CategoryBasicModel categoryBasicModel;
    private Pageable pageable;
    
    @BeforeEach
    void setUp() {
        restaurantBasicModel = new RestaurantBasicModel();
        restaurantBasicModel.setId(1L);
        restaurantBasicModel.setName("Test Restaurant");
        restaurantBasicModel.setOwnerId(1L);
        
        categoryBasicModel = new CategoryBasicModel();
        categoryBasicModel.setId(1L);
        categoryBasicModel.setName("Test Category");
        
        dishModel = new DishModel();
        dishModel.setId(1L);
        dishModel.setName("Test Dish");
        dishModel.setRestaurant(restaurantBasicModel);
        dishModel.setCategory(categoryBasicModel);
        
        restaurantModel = new RestaurantModel();
        restaurantModel.setId(1L);
        restaurantModel.setName("Test Restaurant");
        restaurantModel.setOwnerId(1L);
        
        categoryModel = new CategoryModel();
        categoryModel.setId(1L);
        categoryModel.setName("Test Category");
        
        pageable = PageRequest.of(0, 10);
    }
    
    @Test
    void createDish_Success() {
        when(restaurantPersistencePort.getRestaurantById(1L)).thenReturn(Optional.of(restaurantModel));
        when(categoryPersistencePort.getCategoryById(1L)).thenReturn(Optional.of(categoryModel));
        when(restaurantBasicModelMapper.toRestaurantBasicModel(restaurantModel)).thenReturn(restaurantBasicModel);
        when(categoryBasicModelMapper.toCategoryBasicModel(categoryModel)).thenReturn(categoryBasicModel);
        when(dishPersistencePort.save(dishModel)).thenReturn(dishModel);
        
        DishModel result = dishUseCase.createDish(dishModel);
        
        assertNotNull(result);
        assertTrue(result.getActive());
        assertEquals(restaurantBasicModel, result.getRestaurant());
        assertEquals(categoryBasicModel, result.getCategory());
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
        when(dishPersistencePort.save(existingDish)).thenReturn(existingDish);
        
        DishModel result = dishUseCase.disableEnableDish(requestDish);
        
        assertNotNull(result);
        assertFalse(result.getActive());
        verify(dishPersistencePort).findById(1L);
        verify(restaurantPersistencePort).getRestaurantById(1L);
        verify(dishPersistencePort).save(existingDish);
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

    @Test
    void getAllDishesByRestaurantId_Success_WithoutCategoryFilter() {
        Page<DishModel> expectedPage = new PageImpl<>(List.of(dishModel));
        
        when(restaurantPersistencePort.getRestaurantById(1L)).thenReturn(Optional.of(restaurantModel));
        when(dishPersistencePort.findDishesByRestaurantId(pageable, 1L, null)).thenReturn(expectedPage);
        
        Page<DishModel> result = dishUseCase.getAllDishesByRestaurantId(pageable, 1L, null);
        
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(restaurantPersistencePort).getRestaurantById(1L);
        verify(dishPersistencePort).findDishesByRestaurantId(pageable, 1L, null);
        verify(categoryPersistencePort, never()).getCategoryById(any());
    }

    @Test
    void getAllDishesByRestaurantId_Success_WithCategoryFilter() {
        Page<DishModel> expectedPage = new PageImpl<>(List.of(dishModel));
        
        when(restaurantPersistencePort.getRestaurantById(1L)).thenReturn(Optional.of(restaurantModel));
        when(categoryPersistencePort.getCategoryById(1L)).thenReturn(Optional.of(categoryModel));
        when(dishPersistencePort.findDishesByRestaurantId(pageable, 1L, 1L)).thenReturn(expectedPage);
        
        Page<DishModel> result = dishUseCase.getAllDishesByRestaurantId(pageable, 1L, 1L);
        
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(restaurantPersistencePort).getRestaurantById(1L);
        verify(categoryPersistencePort).getCategoryById(1L);
        verify(dishPersistencePort).findDishesByRestaurantId(pageable, 1L, 1L);
    }

    @Test
    void getAllDishesByRestaurantId_RestaurantNotFound() {
        when(restaurantPersistencePort.getRestaurantById(1L)).thenReturn(Optional.empty());
        doThrow(new BusinessException(ErrorCatalog.RESTAURANT_NOT_FOUND))
            .when(genericValidation).validateCondition(anyBoolean(), eq(ErrorCatalog.RESTAURANT_NOT_FOUND));
        
        assertThrows(BusinessException.class, () -> dishUseCase.getAllDishesByRestaurantId(pageable, 1L, null));
        verify(restaurantPersistencePort).getRestaurantById(1L);
        verify(dishPersistencePort, never()).findDishesByRestaurantId(any(), any(), any());
    }

    @Test
    void getAllDishesByRestaurantId_CategoryNotFound() {
        when(restaurantPersistencePort.getRestaurantById(1L)).thenReturn(Optional.of(restaurantModel));
        when(categoryPersistencePort.getCategoryById(1L)).thenReturn(Optional.empty());
        doThrow(new BusinessException(ErrorCatalog.CATEGORY_NOT_FOUND))
            .when(genericValidation).validateCondition(anyBoolean(), eq(ErrorCatalog.CATEGORY_NOT_FOUND));
        
        assertThrows(BusinessException.class, () -> dishUseCase.getAllDishesByRestaurantId(pageable, 1L, 1L));
        verify(restaurantPersistencePort).getRestaurantById(1L);
        verify(categoryPersistencePort).getCategoryById(1L);
        verify(dishPersistencePort, never()).findDishesByRestaurantId(any(), any(), any());
    }
}