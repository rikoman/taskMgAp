package com.example.backend.controller;

import com.example.backend.DTO.CategoryDTO;
import com.example.backend.entity.Category;
import com.example.backend.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> create(CategoryDTO dto){
        return new ResponseEntity<>(categoryService.createCategory(dto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Category>> readAll(){
        return new ResponseEntity<>(categoryService.readAllCategory(),HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Category> update(Category category){
        return new ResponseEntity<>(categoryService.updateCategory(category),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return HttpStatus.OK;
    }
}
