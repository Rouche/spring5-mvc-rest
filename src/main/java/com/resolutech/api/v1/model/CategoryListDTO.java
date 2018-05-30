package com.resolutech.api.v1.model;

import lombok.Data;

import java.util.List;

@Data
public class CategoryListDTO {

    private final List<CategoryDTO> categories;

    public CategoryListDTO(List<CategoryDTO> categories) {
        this.categories = categories;
    }
}
