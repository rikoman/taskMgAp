package com.example.backend.api.service;

import com.example.backend.api.DTO.CategoryDTO;
import com.example.backend.story.entity.Category;
import com.example.backend.story.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProjectService projectService;

    public Category createCategory(CategoryDTO dto){
        Category category = Category.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .project(projectService.readProjectById(dto.getProjectId()))
                .build();
        return categoryRepository.save(category);
    }

    public List<Category> readAllCategory(){
        return categoryRepository.findAll();
    }

    public Category readCategoryById(Long id){
        return categoryRepository.findById(id).orElseThrow(()->new RuntimeException("Category not found"));
    }

    public List<Category> readAllCategoryByProjectId(Long id){
        return categoryRepository.findByProjectId(id);
    }

    public Category updateCategory(Category category){
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id){
        categoryRepository.deleteById(id);
    }
}
