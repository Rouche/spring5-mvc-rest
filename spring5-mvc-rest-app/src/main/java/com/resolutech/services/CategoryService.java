package com.resolutech.services;

import com.resolutech.api.v1.model.CategoryDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public interface CategoryService {

    List<CategoryDTO> getAll();

    CategoryDTO getByName(String name);

}
