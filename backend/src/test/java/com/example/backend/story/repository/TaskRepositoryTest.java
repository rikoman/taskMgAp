package com.example.backend.story.repository;

import com.example.backend.story.entity.Category;
import com.example.backend.story.entity.Project;
import com.example.backend.story.entity.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TaskRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProjectRepository projectRepository;

    private Task task;

    private final Long id = 1l;

    private final String title = "Buy milk";

    private final String description = "buy milk in supermarket";

    private final Integer priority = 3;

    private final Boolean status = true;

    private final LocalDate time = LocalDate.now();

    private final Project project = Project.builder()
            .title("Project Title")
            .build();

    private final Category category = Category.builder()
            .title("category Title")
            .project(project)
            .build();

    private final Task taskParent = Task.builder()
            .title("title")
            .project(project)
            .build();

    @BeforeEach
    void prepareData() {
        task = Task.builder()
                .id(id)
                .title(title)
                .description(description)
                .priority(priority)
                .status(status)
                .project(project)
                .category(category)
                .parent(taskParent)
                .dateCreate(time)
                .build();

        projectRepository.save(project);

        categoryRepository.save(category);
    }

    @Test
    void saveTest(){
        Task savedTask = repository.save(task);

        Task task1 = repository.findById(savedTask.getId()).orElseThrow();

        assertEquals(savedTask.getId(),task1.getId());
        assertEquals(savedTask.getTitle(),task1.getTitle());
        assertEquals(savedTask.getDescription(),task1.getDescription());
        assertEquals(savedTask.getPriority(),task1.getPriority());
        assertEquals(savedTask.getStatus(),task1.getStatus());
        assertEquals(savedTask.getProject(),task1.getProject());
        assertEquals(savedTask.getCategory(),task1.getCategory());
        assertEquals(savedTask.getParent(),task1.getParent());
        assertEquals(savedTask.getDateCreate(),task1.getDateCreate());
    }

    @Test
    void findAllTest(){
        Task task1 = Mockito.mock(Task.class);
        Task task2 = Mockito.mock(Task.class);
        Task task3 = Mockito.mock(Task.class);

        repository.saveAll(List.of(task1,task2,task3));

        List<Task> result = repository.findAll();

        assertEquals(3,result.size());
    }

    @Test
    void updateTest(){
        Task savedTask = repository.save(task);

        String newTitle = "new Title";
        String newDescription = "new Description";
        Integer newPriority = 1;
        Boolean newStatus = true;
        LocalDate newDate = LocalDate.now();

        Project newProject = Project.builder()
                .title("Project Title")
                .build();

        Category newCategory = Category.builder()
                .title("category Title")
                .project(project)
                .build();

        Task newTaskParent = Task.builder()
                .title("title")
                .project(project)
                .build();

        savedTask.setId(id);
        savedTask.setTitle(newTitle);
        savedTask.setDescription(newDescription);
        savedTask.setPriority(newPriority);
        savedTask.setStatus(newStatus);
        savedTask.setProject(newProject);
        savedTask.setCategory(newCategory);
        savedTask.setParent(newTaskParent);
        savedTask.setDateCreate(newDate);

        Task updatedTask = repository.save(savedTask);

        assertEquals(id,updatedTask.getId());
        assertEquals(newTitle,updatedTask.getTitle());
        assertEquals(newDescription,updatedTask.getDescription());
        assertEquals(newPriority,updatedTask.getPriority());
        assertEquals(newStatus,updatedTask.getStatus());
        assertEquals(newProject,updatedTask.getProject());
        assertEquals(newCategory,updatedTask.getCategory());
        assertEquals(newTaskParent,updatedTask.getParent());
        assertEquals(newDate,updatedTask.getDateCreate());
    }

    @Test
    void deleteByIdTest(){
        Task savedTask = repository.save(task);

        repository.deleteById(savedTask.getId());

        Optional<Task> task1 = repository.findById(savedTask.getId());

        assertTrue(task1.isEmpty());
    }

    @Test
    void findByProjectIdAndStatusTrueTest() {
        Task task1 = Task.builder()
                .title(title)
                .description(description)
                .priority(priority)
                .status(true)
                .project(project)
                .dateCreate(time)
                .build();

        Task task2 = Task.builder()
                .title(title)
                .description(description)
                .priority(priority)
                .status(false)
                .project(project)
                .dateCreate(time)
                .build();

        repository.saveAll(List.of(task1,task2));

        List<Task> result = repository.findByProjectIdAndStatusTrue(project.getId());

        assertEquals(1,result.size());

    }

    @Test
    void findByCategoryIdAndStatusTrueTest() {
        Task task1 = Task.builder()
                .title(title)
                .description(description)
                .priority(priority)
                .status(true)
                .category(category)
                .dateCreate(time)
                .build();

        Task task2 = Task.builder()
                .title(title)
                .description(description)
                .priority(priority)
                .status(false)
                .category(category)
                .dateCreate(time)
                .build();

        repository.saveAll(List.of(task1,task2));

        List<Task> result = repository.findByCategoryIdAndStatusTrue(category.getId());

        assertEquals(1,result.size());
    }

    @Test
    void findByStatusFalseTest() {
        Task task1 = Task.builder()
                .title(title)
                .description(description)
                .priority(priority)
                .status(true)
                .dateCreate(time)
                .build();

        Task task2 = Task.builder()
                .title(title)
                .description(description)
                .priority(priority)
                .status(false)
                .dateCreate(time)
                .build();

        repository.saveAll(List.of(task1,task2));

        List<Task> result = repository.findByStatusFalse();

        assertEquals(1,result.size());
    }
}