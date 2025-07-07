package com.project.foodCourt.infrastructure.out.jpa.adapter;

import com.project.foodCourt.domain.model.CategoryModel;
import com.project.foodCourt.infrastructure.out.jpa.entity.CategoryEntity;
import com.project.foodCourt.infrastructure.out.jpa.mapper.ICategoryEntityMapper;
import com.project.foodCourt.infrastructure.out.jpa.repository.ICategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryJpaAdapterTest {

    @Mock
    private ICategoryEntityMapper categoryEntityMapper;
    
    @Mock
    private ICategoryRepository categoryRepository;
    
    @InjectMocks
    private CategoryJpaAdapter categoryJpaAdapter;
    
    private CategoryEntity categoryEntity;
    private CategoryModel categoryModel;
    
    @BeforeEach
    void setUp() {
        categoryEntity = new CategoryEntity();
        categoryEntity.setId(1L);
        categoryEntity.setName("Test Category");
        
        categoryModel = new CategoryModel();
        categoryModel.setId(1L);
        categoryModel.setName("Test Category");
    }
    
    @Test
    void getCategoryById_Found() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(categoryEntity));
        when(categoryEntityMapper.toCategoryModel(categoryEntity)).thenReturn(categoryModel);
        
        Optional<CategoryModel> result = categoryJpaAdapter.getCategoryById(1L);
        
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals("Test Category", result.get().getName());
    }
    
    @Test
    void getCategoryById_NotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());
        
        Optional<CategoryModel> result = categoryJpaAdapter.getCategoryById(1L);
        
        assertFalse(result.isPresent());
    }
}