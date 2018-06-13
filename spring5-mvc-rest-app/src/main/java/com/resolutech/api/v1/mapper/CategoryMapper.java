package com.resolutech.api.v1.mapper;

import com.resolutech.api.v1.model.CategoryDTO;
import com.resolutech.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

// @Important https://github.com/mapstruct/mapstruct/issues/1365
// Need to put interface public or else IllegalAccessException
@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDTO categoryToCategoryDTO(Category category);
}