package com.example.backend.api.service;

import com.example.backend.story.DTO.CategoryDTO;
import com.example.backend.api.exception.BadRequestException;
import com.example.backend.api.exception.NotFoundException;
import com.example.backend.story.entity.Category;
import com.example.backend.story.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProjectService projectService;

    @CacheEvict(cacheNames = {"categories", "categoriesByProjectId"}, allEntries = true)
    public Category createCategory(CategoryDTO dto){
        if(dto.getTitle() == null || dto.getProjectId() == null) throw new BadRequestException("Invalid request");

        Category category = Category.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .project(projectService.readProjectById(dto.getProjectId()))
                .build();
        return categoryRepository.save(category);
    }

    @Cacheable(cacheNames = "categories")
    public Page<Category> readAllCategory(PageRequest pageRequest){
        return categoryRepository.findAll(pageRequest);
    }

    @Cacheable(cacheNames = "category", key = "#id")
    public Category readCategoryById(Long id){
        return categoryRepository.findById(id).orElseThrow(()->new NotFoundException(String.format("Category with /%s/ id doesn' found",id)));
    }

    @Cacheable(cacheNames = "categoriesByProjectId",key = "#id")
    public Page<Category> readAllCategoryByProjectId(Long id, PageRequest pageRequest){
        projectService.readProjectById(id);
        return categoryRepository.findByProjectId(id, pageRequest);
    }

    @Caching(evict = { @CacheEvict(cacheNames = "category", key = "#category.id"),
                       @CacheEvict(cacheNames = {"categories", "categoriesByProjectId"}, allEntries = true)})
    public Category updateCategory(Category category){
        if(category.getId() == null || category.getTitle() == null || category.getProject() == null) throw new BadRequestException("Invalid request");
        return categoryRepository.save(category);
    }

    @Caching(evict = { @CacheEvict(cacheNames = "category", key = "#category.id"),
            @CacheEvict(cacheNames = {"categories", "categoriesByProjectId"}, allEntries = true)})
    public Category updatePartInfoForCategory(Category category){
        Category existCategory = readCategoryById(category.getId());

        if(category.getTitle() != null){
            existCategory.setTitle(category.getTitle());
        }

        if(category.getDescription() != null){
            existCategory.setDescription(category.getDescription());
        }

        if(category.getProject() != null){
            projectService.readProjectById(category.getProject().getId());
            existCategory.setProject(category.getProject());
        }

        return categoryRepository.save(existCategory);
    }

    @Caching(evict = { @CacheEvict(cacheNames = "category", key = "#id"),
                       @CacheEvict(cacheNames = {"categories", "categoriesByProjectId"}, allEntries = true)})
    public void deleteCategory(Long id){
        readCategoryById(id);
        categoryRepository.deleteById(id);
    }
}
