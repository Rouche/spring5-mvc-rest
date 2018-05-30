package com.resolutech.services;

import com.resolutech.api.v1.mapper.CategoryMapper;
import com.resolutech.api.v1.model.CategoryDTO;
import com.resolutech.domain.Category;
import com.resolutech.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class CategoryServiceTest {

    public static final String NAME = "Zomg";

    @Mock
    CategoryRepository categoryRepository;

    CategoryService categoryService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        categoryService = new CategoryServiceImpl(CategoryMapper.INSTANCE, categoryRepository);
    }

    @Test
    public void testGetAll() {
        //Given
        List<Category> list = Arrays.asList(Category.builder().name(NAME).build(), Category.builder().name(NAME).build());

        when(categoryRepository.findAll()).thenReturn(list);

        //When
        List<CategoryDTO> dtoList = categoryService.getAll();

        //Then
        assertNotNull(dtoList);
        assertEquals(2, dtoList.size());
    }

    @Test
    public void testGetByName() {

        //Given
        Category category = Category.builder().name(NAME).build();

        when(categoryRepository.findByName(NAME)).thenReturn(category);

        //When
        CategoryDTO dto = categoryService.getByName(NAME);

        //Then
        assertNotNull(dto);
        assertEquals(NAME, dto.getName());
    }
}