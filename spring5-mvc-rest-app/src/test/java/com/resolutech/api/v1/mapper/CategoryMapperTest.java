package com.resolutech.api.v1.mapper;

import com.resolutech.api.v1.model.CategoryDTO;
import com.resolutech.domain.Category;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryMapperTest {

    public static final Long ID = 1L;
    public static final String NAME = "Joe";

    @Test
    public void categoryToCategoryDTO() {
        //Given
        Category category = Category.builder().id(ID).name(NAME).build();

        //When
        CategoryDTO dto = CategoryMapper.INSTANCE.categoryToCategoryDTO(category);

        //Then
        assertEquals(ID, dto.getId());
        assertEquals(NAME, dto.getName());
    }
}