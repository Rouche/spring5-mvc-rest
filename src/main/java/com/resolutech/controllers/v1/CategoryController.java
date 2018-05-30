package com.resolutech.controllers.v1;

import com.resolutech.api.v1.model.CategoryDTO;
import com.resolutech.api.v1.model.CategoryListDTO;
import com.resolutech.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1/categories/")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<CategoryListDTO> getAll() {
        return new ResponseEntity(new CategoryListDTO(categoryService.getAll()), HttpStatus.OK);
    }

    @GetMapping("{name}")
    public ResponseEntity<CategoryDTO> getByName(@PathVariable("name") String name) {
        return new ResponseEntity(categoryService.getByName(name), HttpStatus.OK);
    }
}
