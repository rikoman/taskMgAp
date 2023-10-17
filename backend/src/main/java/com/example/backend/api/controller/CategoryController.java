package com.example.backend.api.controller;

import com.example.backend.api.DTO.CategoryDTO;
import com.example.backend.story.entity.Category;
import com.example.backend.api.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDTO dto){
        return mappingResponseCategory(categoryService.createCategory(dto));
    }

    @GetMapping
    public ResponseEntity<List<Category>> readAllCategory(){
        return mappingResponseListCategory(categoryService.readAllCategory());
    }

    @GetMapping("/data/{id}")
    public ResponseEntity<Category> readById(@PathVariable Long id){
        return mappingResponseCategory(categoryService.readCategoryById(id));
    }

    @GetMapping("/project/{id}")
    public ResponseEntity<List<Category>> readAllCategoryByProjectId(@PathVariable Long id){
        return mappingResponseListCategory(categoryService.readAllCategoryByProjectId(id));
    }

    @PutMapping
    public ResponseEntity<Category> updateCategory(@RequestBody Category category){
        return mappingResponseCategory(categoryService.updateCategory(category));
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return HttpStatus.OK;
    }

    private ResponseEntity<Category> mappingResponseCategory(Category category){
        return new ResponseEntity<>(category,HttpStatus.OK);
    }

    private ResponseEntity<List<Category>> mappingResponseListCategory(List<Category> category){
        return new ResponseEntity<>(category,HttpStatus.OK);
    }
}
