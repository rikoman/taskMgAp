package com.example.backend.story.repository;

import com.example.backend.story.entity.Category;
import com.example.backend.story.entity.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private ProjectRepository projectRepository;

    private Category category;

    private final Long id = 1l;

    private final String title = "title";

    private final String description = "description";

    private final Project project = Project.builder()
            .title("Project Title")
            .build();

    @BeforeEach
    void prepareData() {
        category = Category.builder()
                .id(id)
                .title(title)
                .description(description)
                .project(project)
                .build();

        projectRepository.save(project);
    }

    @Test
    void saveTest(){
        repository.save(category);

        Category category1 = repository.findById(id).orElseThrow();

        assertEquals(id,category1.getId());
        assertEquals(title,category1.getTitle());
        assertEquals(description,category1.getDescription());
    }

    @Test
    void findAllTest(){
        Category category1 = Mockito.mock(Category.class);
        Category category2 = Mockito.mock(Category.class);
        Category category3 = Mockito.mock(Category.class);

        repository.saveAll(List.of(category1,category2,category3));

        List<Category> result = repository.findAll();

        assertEquals(3,result.size());
    }

    @Test
    void updateTest(){
        Category savedCategory = repository.save(category);

        String newTitle = "new Title";
        String newDescription = "new Description";
        Project newProject = Project.builder()
                .title("Project Title")
                .build();

        savedCategory.setId(id);
        savedCategory.setTitle(newTitle);
        savedCategory.setDescription(newDescription);
        savedCategory.setProject(newProject);

        Category updatedCategory = repository.save(savedCategory);

        assertEquals(id, updatedCategory.getId());
        assertEquals(newTitle,updatedCategory.getTitle());
        assertEquals(newDescription, updatedCategory.getDescription());
        assertEquals(newProject, updatedCategory.getProject());
    }

    @Test
    void deleteByIdTest(){
        repository.save(category);

        repository.deleteById(id);

        Optional<Category> category1 = repository.findById(id);

        assertTrue(category1.isEmpty());
    }

    @Test
    void findByProjectIdTest(){
        Category category1 = Category.builder()
                .title(title)
                .description(description)
                .project(project)
                .build();

        repository.save(category1);

        List<Category> result = repository.findByProjectId(project.getId());

        assertNotNull(result);
    }
}