package com.example.backend.api.service;

import com.example.backend.api.DTO.CategoryDTO;
import com.example.backend.api.exception.BadRequestException;
import com.example.backend.api.exception.NotFoundException;
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
        if(dto.getTitle() == null || dto.getProjectId() == null) throw new BadRequestException("Invalid request");

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
        return categoryRepository.findById(id).orElseThrow(()->new NotFoundException(String.format("Category with %s id doesn' found",id)));
    }

    public List<Category> readAllCategoryByProjectId(Long id){
        projectService.readProjectById(id);
        return categoryRepository.findByProjectId(id);
    }

    public Category updateCategory(Category category){
        if(category.getId() == null || category.getTitle() == null || category.getProject() == null) throw new BadRequestException("Invalid request");
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id){
        readCategoryById(id);
        categoryRepository.deleteById(id);
    }
}