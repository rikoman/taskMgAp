package com.example.backend.api.service;

import com.example.backend.story.DTO.CategoryDTO;
import com.example.backend.api.exception.NotFoundException;
import com.example.backend.story.entity.Category;
import com.example.backend.story.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProjectService projectService;

    @Transactional
//    @CacheEvict(cacheNames = {"categories", "categoriesByProjectId"}, allEntries = true)
    public Category createCategory(CategoryDTO dto, Authentication authentication){
        Category category = Category.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .project(projectService.readProjectById(dto.getProjectId(),authentication))
                .build();
        return categoryRepository.save(category);
    }

    @Transactional
//    @Cacheable(cacheNames = "categories")
    public Page<Category> readAllCategory(PageRequest pageRequest){
        return categoryRepository.findAll(pageRequest);
    }

    @Transactional
//    @Cacheable(cacheNames = "category", key = "#id")
    public Category readCategoryById(Long id,Authentication authentication){
        Category existCategory = categoryRepository.findById(id).orElseThrow(()->new NotFoundException(String.format("Category with /%s/ id doesn' found",id)));
        projectService.readProjectById(existCategory.getProject().getId(),authentication);
        return existCategory;
    }

    @Transactional
//    @Cacheable(cacheNames = "categoriesByProjectId",key = "#id")
    public Page<Category> readAllCategoryByProjectId(Long id, PageRequest pageRequest, Authentication authentication){
        projectService.readProjectById(id,authentication);
        return categoryRepository.findByProjectId(id, pageRequest);
    }

//    @Caching(evict = { @CacheEvict(cacheNames = "category", key = "#category.id"),
//                       @CacheEvict(cacheNames = {"categories", "categoriesByProjectId"}, allEntries = true)})
//    public Category updateCategory(Category category){
//        if(category.getId() == null || category.getTitle() == null || category.getProject() == null) throw new BadRequestException("Invalid request");
//        return categoryRepository.save(category);
//    }

    @Transactional
//    @Caching(evict = { @CacheEvict(cacheNames = "category", key = "#category.id"),
//                       @CacheEvict(cacheNames = {"categories", "categoriesByProjectId"}, allEntries = true)})
    public Category updatePartInfoForCategory(Long id,CategoryDTO dto, Authentication authentication){
        Category existCategory = readCategoryById(id,authentication);

        if(dto.getTitle() != null){
            existCategory.setTitle(dto.getTitle());
        }

        if(dto.getDescription() != null){
            existCategory.setDescription(dto.getDescription());
        }

        return categoryRepository.save(existCategory);
    }

    @Transactional
//    @Caching(evict = { @CacheEvict(cacheNames = "category", key = "#id"),
//                       @CacheEvict(cacheNames = {"categories", "categoriesByProjectId"}, allEntries = true)})
    public void deleteCategory(Long id, Authentication authentication){
        readCategoryById(id, authentication);
        categoryRepository.deleteById(id);
    }
}
