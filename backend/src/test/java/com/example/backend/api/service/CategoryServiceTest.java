package com.example.backend.api.service;

import com.example.backend.api.exception.BadRequestException;
import com.example.backend.story.DTO.CategoryDTO;
import com.example.backend.story.entity.Category;
import com.example.backend.story.entity.Project;
import com.example.backend.story.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProjectService projectService;

    private final CategoryDTO categoryDTO = new CategoryDTO();

    private Category category;

    private final Project project = Project.builder()
            .title("Project Title")
            .build();

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void prepareDate(){
        categoryDTO.setTitle("title");
        categoryDTO.setDescription("Description");
        categoryDTO.setProjectId(project.getId());

        category = Category.builder()
                .id(1L)
                .title(categoryDTO.getTitle())
                .description(categoryDTO.getDescription())
                .project(project)
                .build();
    }

    @Test
    void createCategory_invalidRequest_throwBadRequestExceptionTest() {
        categoryDTO.setTitle(null);
        categoryDTO.setProjectId(null);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> categoryService.createCategory(categoryDTO));

        assertEquals("Invalid request", exception.getMessage());
    }

    @Test
    void createCategory_validRequest_saveCategoryTest() {
        CategoryDTO dto = new CategoryDTO("TestCategory", "TestDescription", 1L);

        when(projectService.readProjectById(1L)).thenReturn(project);

        categoryService.createCategory(dto);

        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void readAllCategoryTest() {
        categoryService.readAllCategory();

        verify(categoryRepository,times(1)).findAll();
    }

    @Test
    void readCategoryByIdTest() {
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));

        categoryService.readCategoryById(category.getId());

        verify(categoryRepository, times(1)).findById(category.getId());
    }

    @Test
    void readAllCategoryByProjectIdTest() {
        categoryService.readAllCategoryByProjectId(project.getId());

        verify(categoryRepository, times(1)).findByProjectId(project.getId());
    }

    @Test
    void updateCategoryTest() {
        category.setId(1L);

        categoryService.updateCategory(category);

        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void updatePartInfoForCategoryTest() {
        Category existingCategory = Category.builder()
                .id(category.getId())
                .description("Existing Description")
                .project(new Project())
                .build();

        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(existingCategory));

        categoryService.updatePartInfoForCategory(category);

        verify(categoryRepository).findById(category.getId());
        verify(categoryRepository).save(existingCategory);
    }

    @Test
    void deleteCategoryTest() {
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));

        categoryService.deleteCategory(category.getId());

        verify(categoryRepository, times(1)).deleteById(category.getId());
    }
}