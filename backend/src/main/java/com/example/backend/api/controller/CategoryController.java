package com.example.backend.api.controller;

import com.example.backend.story.DTO.CategoryDTO;
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

    private final MappingResponse<Category> mappingResponse;

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDTO dto){
        return mappingResponse.entity(categoryService.createCategory(dto));
    }

    @GetMapping
    public ResponseEntity<List<Category>> readAllCategory(){
        return mappingResponse.listEntity(categoryService.readAllCategory());
    }

    @GetMapping("/data/{id}")
    public ResponseEntity<Category> readById(@PathVariable Long id){
        return mappingResponse.entity(categoryService.readCategoryById(id));
    }

    @GetMapping("/project/{id}")
    public ResponseEntity<List<Category>> readAllCategoryByProjectId(@PathVariable Long id){
        return mappingResponse.listEntity(categoryService.readAllCategoryByProjectId(id));
    }

    @PutMapping
    public ResponseEntity<Category> updateCategory(@RequestBody Category category){
        return mappingResponse.entity(categoryService.updateCategory(category));
    }

    @PatchMapping
    public ResponseEntity<Category> updatePartInfo(@RequestBody Category category){
        return mappingResponse.entity(categoryService.updatePartInfoForCategory(category));
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return HttpStatus.OK;
    }
}
