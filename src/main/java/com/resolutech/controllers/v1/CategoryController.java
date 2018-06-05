package com.resolutech.controllers.v1;

import com.resolutech.api.v1.model.CategoryDTO;
import com.resolutech.api.v1.model.CategoryListDTO;
import com.resolutech.controllers.RestResponseEntityExceptionHandler;
import com.resolutech.services.CategoryService;
import com.resolutech.services.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

// @Important combines Controller + ResponseBody so we dont need to return ResponseEntity
@RestController
@RequestMapping(CategoryController.BASE_URL)
public class CategoryController {

    protected static final String BASE_URL = "api/v1/categories";

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CategoryListDTO getAll() {
        return new CategoryListDTO(categoryService.getAll());
    }

    @GetMapping("{name}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO getByName(@PathVariable("name") String name) {
        return categoryService.getByName(name);
    }
}
