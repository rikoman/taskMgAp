package com.example.backend.story.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    Long id = 1l;

    String title = "Buy milk";

    String description = "buy milk in supermarket";

    Integer priority = 3;

    Boolean status = true;

    LocalDate time = LocalDate.now();

    Project project = Project.builder()
            .title("Project Title")
            .build();
    Category category = Category.builder()
            .title("category Title")
            .project(project)
            .build();
    Task taskParent = Task.builder()
            .title("title")
            .project(project)
            .build();
    Task task;

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
    }

    @Test
    void getId() {
        assertEquals(id, task.getId());
    }

    @Test
    void getTitle() {
        assertEquals(title, task.getTitle());
    }

    @Test
    void getStatus() {
        assertEquals(status, task.getStatus());
    }

    @Test
    void getDescription() {
        assertEquals(description, task.getDescription());
    }

    @Test
    void getPriority() {
        assertEquals(priority, task.getPriority());
    }

    @Test
    void getProject() {
        assertEquals(project,task.getProject());
    }

    @Test
    void getCategory() {
        assertEquals(category,task.getCategory());
    }

    @Test
    void getParent() {
        assertEquals(taskParent,task.getParent());
    }

    @Test
    void getDateCreate() {
        assertEquals(time,task.getDateCreate());
    }

    @Test
    void setId() {
        Long newId = 2l;

        task.setId(newId);

        assertEquals(newId, task.getId());
    }

    @Test
    void setTitle() {
        String newTitle = "buy car";

        task.setTitle(newTitle);

        assertEquals(newTitle, task.getTitle());
    }

    @Test
    void setStatus() {
        Boolean newStatus = false;

        task.setStatus(newStatus);

        assertEquals(newStatus, task.getStatus());
    }

    @Test
    void setDescription() {
        String newDescription = "buy milk in other city";

        task.setDescription(newDescription);

        assertEquals(newDescription, task.getDescription());
    }

    @Test
    void setPriority() {
        Integer newPriority = 3;

        task.setPriority(newPriority);

        assertEquals(newPriority, task.getPriority());
    }

    @Test
    void setProject() {
        Project newProject = Project.builder()
                .title("Title")
                .build();

        task.setProject(newProject);

        assertEquals(newProject, task.getProject());
    }

    @Test
    void setCategory() {
        Category newCategory = Category.builder()
                .title("Title")
                .project(project)
                .build();

        task.setCategory(newCategory);

        assertEquals(newCategory, task.getCategory());
    }

    @Test
    void setTaskParent() {
        Task newTask = Task.builder()
                .title("Task title")
                .project(project)
                .build();

        task.setParent(newTask);

        assertEquals(newTask, task.getParent());
    }

    @Test
    void setDateCreate() {
        LocalDate newDate = LocalDate.now();

        task.setDateCreate(newDate);

        assertEquals(newDate,task.getDateCreate());
    }

    @Test
    void testEquals() {
        Task task = Task.builder()
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

        assertEquals(this.task,task);
    }

    @Test
    void canEqual() {
        assertTrue(task.canEqual(task));
    }

    @Test
    void testHashCode() {
        assertNotEquals(0,task.hashCode());
    }

    @Test
    void testToString() {
        assertNotNull(task.toString());
    }

    @Test
    void builder() {
        assertEquals(id,task.getId());
        assertEquals(title,task.getTitle());
        assertEquals(description,task.getDescription());
        assertEquals(priority,task.getPriority());
        assertEquals(status,task.getStatus());
        assertEquals(project,task.getProject());
        assertEquals(category,task.getCategory());
        assertEquals(taskParent,task.getParent());
        assertEquals(time,task.getDateCreate());
    }
}