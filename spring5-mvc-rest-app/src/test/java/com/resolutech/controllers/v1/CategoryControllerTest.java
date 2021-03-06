package com.resolutech.controllers.v1;

import com.resolutech.api.v1.model.CategoryDTO;
import com.resolutech.controllers.RestResponseEntityExceptionHandler;
import com.resolutech.controllers.v1.CategoryController;
import com.resolutech.services.CategoryService;
import com.resolutech.services.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CategoryControllerTest {

    public static final String NAME = "Joe";
    @Mock
    CategoryService categoryService;

    // @Important InjectMocks will inject mocks automatically
    @InjectMocks
    CategoryController categoryController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(categoryController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
    }

    @Test
    public void testGetAll() throws Exception {
        CategoryDTO category1 = new CategoryDTO();
        category1.setId(1l);
        category1.setName("Joe");

        CategoryDTO category2 = new CategoryDTO();
        category2.setId(2l);
        category2.setName("Bob");

        List<CategoryDTO> categories = Arrays.asList(category1, category2);

        when(categoryService.getAll()).thenReturn(categories);

        mockMvc.perform(get("/api/v1/categories/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories", hasSize(2)));    }

    @Test
    public void testGetByName() throws Exception {
        CategoryDTO category1 = new CategoryDTO();
        category1.setId(1l);
        category1.setName(NAME);

        when(categoryService.getByName(NAME)).thenReturn(category1);

        mockMvc.perform(get("/api/v1/categories/" + NAME)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)));
    }

    @Test
    public void testByNameNotFound() throws Exception {
        when(categoryService.getByName(NAME)).thenThrow(new ResourceNotFoundException());

        mockMvc.perform(get("/api/v1/categories/" + NAME)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}