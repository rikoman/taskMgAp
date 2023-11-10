package com.example.backend.api.service;

import com.example.backend.api.exception.BadRequestException;
import com.example.backend.story.DTO.TaskDTO;
import com.example.backend.story.entity.Category;
import com.example.backend.story.entity.Project;
import com.example.backend.story.entity.Task;
import com.example.backend.story.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ProjectService projectService;

    @Mock
    private CategoryService categoryService;

    private Task task;

    private final TaskDTO dto = new TaskDTO();

    private final Project project = Project.builder()
            .id(1L)
            .title("Project Title")
            .build();

    private final Category category = Category.builder()
            .id(1L)
            .title("category Title")
            .project(project)
            .build();

    private final Task taskParent = Task.builder()
            .id(1L)
            .title("title")
            .project(project)
            .build();

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void prepareDate(){
        TaskDTO localDTO = new TaskDTO();

        localDTO.setTitle("Title");
        localDTO.setStatus(true);
        localDTO.setProjectId(project.getId());

        task = Task.builder()
                .id(2L)
                .title(localDTO.getTitle())
                .description(localDTO.getDescription())
                .status(localDTO.getStatus())
                .project(project)
                .build();
    }

    @Test
    public void createTaskWithTitleNullTest() {
        dto.setStatus(true);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> taskService.createTask(dto));

        assertEquals("Invalid request", exception.getMessage());
    }

    @Test
    public void createTaskWithStatusNullTest() {
        dto.setTitle("title");

        BadRequestException exception = assertThrows(BadRequestException.class, () -> taskService.createTask(dto));

        assertEquals("Invalid request", exception.getMessage());
    }

    @Test
    public void createTaskWithProjectIdAndCategoryIdNotNullTest() {
        dto.setTitle("title");
        dto.setStatus(true);
        dto.setProjectId(project.getId());
        dto.setCategoryId(category.getId());

        BadRequestException exception = assertThrows(BadRequestException.class, () -> taskService.createTask(dto));

        assertEquals("Invalid request", exception.getMessage());
    }

    @Test
    public void createTaskWithProjectIdAndParentIdNotNullTest() {
        dto.setTitle("title");
        dto.setStatus(true);
        dto.setProjectId(project.getId());
        dto.setParentId(taskParent.getId());

        BadRequestException exception = assertThrows(BadRequestException.class, () -> taskService.createTask(dto));

        assertEquals("Invalid request", exception.getMessage());
    }

    @Test
    public void createTaskWithCategoryIdAndParentIdNotNullTest() {
        dto.setTitle("title");
        dto.setStatus(true);
        dto.setCategoryId(category.getId());
        dto.setParentId(taskParent.getId());

        BadRequestException exception = assertThrows(BadRequestException.class, () -> taskService.createTask(dto));

        assertEquals("Invalid request", exception.getMessage());
    }

    @Test
    void createTask_Valid_WithCategoryTest(){
        dto.setTitle("title");
        dto.setStatus(true);
        dto.setCategoryId(category.getId());

        taskService.createTask(dto);

        verify(taskRepository,times(1)).save(any(Task.class));
    }
    @Test
    void createTask_Valid_WithProjectTest(){
        dto.setTitle("title");
        dto.setStatus(true);
        dto.setProjectId(project.getId());

        taskService.createTask(dto);

        verify(taskRepository,times(1)).save(any(Task.class));
    }
    @Test
    void createTask_Valid_WithParentTaskTest(){
        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));

        dto.setTitle("title");
        dto.setStatus(true);
        dto.setParentId(task.getId());

        taskService.createTask(dto);

        verify(taskRepository,times(1)).save(any(Task.class));
    }

    @Test
    void readAllTaskTest() {
        taskService.readAllTask();

        verify(taskRepository,times(1)).findAll();
    }

    @Test
    void readAllTaskByStatusFalseTest() {
        taskService.readAllTaskByStatusFalse();

        verify(taskRepository,times(1)).findByStatusFalse();
    }

    @Test
    void readTaskByIdTest() {
        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));

        taskService.readTaskById(task.getId());

        verify(taskRepository, times(1)).findById(task.getId());
    }

    @Test
    void readAllTaskByCategoryIdTest() {
        taskService.readAllTaskByCategoryId(category.getId());

        verify(taskRepository, times(1)).findByCategoryIdAndStatusTrue(category.getId());
    }

    @Test
    void readAllTaskByProjectIdTest() {
        taskService.readAllTaskByProjectId(project.getId());

        verify(taskRepository, times(1)).findByProjectIdAndStatusTrue(project.getId());
    }

    @Test
    void updateTaskTest() {
        taskService.updateTask(task);

        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void updatePartInfoForTaskTest() {
        Task existingTask = Task.builder()
                .id(task.getId())
                .title("Content")
                .status(true)
                .project(project)
                .build();

        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(existingTask));

        taskService.updatePartInfoForTask(task);

        verify(taskRepository).findById(task.getId());
        verify(taskRepository).save(existingTask);
    }

    @Test
    void deleteTaskTest() {
        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));

        taskService.deleteTask(task.getId());

        verify(taskRepository, times(1)).deleteById(task.getId());
    }
}