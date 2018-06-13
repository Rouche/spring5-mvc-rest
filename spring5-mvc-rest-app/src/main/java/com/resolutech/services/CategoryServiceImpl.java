package com.resolutech.services;

import com.resolutech.api.v1.mapper.CategoryMapper;
import com.resolutech.api.v1.model.CategoryDTO;
import com.resolutech.domain.Category;
import com.resolutech.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryMapper categoryMapper, CategoryRepository categoryRepository) {
        this.categoryMapper = categoryMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDTO> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::categoryToCategoryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getByName(String name) {
        Category category = categoryRepository.findByName(name);
        if(category == null) {
            throw new ResourceNotFoundException();
        }
        return categoryMapper.categoryToCategoryDTO(category);
    }
}
