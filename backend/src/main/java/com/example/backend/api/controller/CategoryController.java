package com.example.backend.api.controller;

import com.example.backend.api.component.MappingResponse;
import com.example.backend.api.component.PageData;
import com.example.backend.story.DTO.CategoryDTO;
import com.example.backend.story.DTO.PageDataDTO;
import com.example.backend.story.entity.Category;
import com.example.backend.api.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final MappingResponse<Category> mappingResponse;
    private final PageData<Category> pageData;

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDTO dto){
        return mappingResponse.entity(categoryService.createCategory(dto));
    }

    @GetMapping
    public ResponseEntity<PageDataDTO<Category>> readAllCategory(
            @RequestParam(required = false,defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ){
        return mappingResponse.listEntity(pageData.pageDataDTO(categoryService.readAllCategory(PageRequest.of(page, size))));
    }

    @GetMapping("/data/{id}")
    public ResponseEntity<Category> readById(@PathVariable Long id){
        return mappingResponse.entity(categoryService.readCategoryById(id));
    }

    @GetMapping("/project/{id}")
    public ResponseEntity<PageDataDTO<Category>> readAllCategoryByProjectId(
            @PathVariable Long id,
            @RequestParam(required = false,defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ){
        return mappingResponse.listEntity(pageData.pageDataDTO(categoryService.readAllCategoryByProjectId(id,PageRequest.of(page,size))));
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
