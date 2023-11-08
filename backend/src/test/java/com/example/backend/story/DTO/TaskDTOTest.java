package com.example.backend.story.DTO;

import com.example.backend.story.entity.Category;
import com.example.backend.story.entity.Project;
import com.example.backend.story.entity.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TaskDTOTest {

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

    private final TaskDTO dto = new TaskDTO();

    @BeforeEach
    void prepareData() {
        dto.setTitle(title);
        dto.setDescription(description);
        dto.setPriority(priority);
        dto.setStatus(status);
        dto.setProjectId(project.getId());
        dto.setCategoryId(category.getId());
        dto.setParentId(taskParent.getId());
        dto.setDateCreate(time);
    }

    @Test
    void getTitle() {
        assertEquals(title, dto.getTitle());
    }

    @Test
    void getStatus() {
        assertEquals(status, dto.getStatus());
    }

    @Test
    void getDescription() {
        assertEquals(description, dto.getDescription());
    }

    @Test
    void getPriority() {
        assertEquals(priority, dto.getPriority());
    }

    @Test
    void getProject() {
        assertEquals(project.getId(),dto.getProjectId());
    }

    @Test
    void getCategory() {
        assertEquals(category.getId(),dto.getCategoryId());
    }

    @Test
    void getParent() {
        assertEquals(taskParent.getId(),dto.getParentId());
    }

    @Test
    void getDateCreate() {
        assertEquals(time,dto.getDateCreate());
    }

    @Test
    void setTitle() {
        String newTitle = "buy car";

        dto.setTitle(newTitle);

        assertEquals(newTitle, dto.getTitle());
    }

    @Test
    void setStatus() {
        Boolean newStatus = false;

        dto.setStatus(newStatus);

        assertEquals(newStatus, dto.getStatus());
    }

    @Test
    void setDescription() {
        String newDescription = "buy milk in other city";

        dto.setDescription(newDescription);

        assertEquals(newDescription, dto.getDescription());
    }

    @Test
    void setPriority() {
        Integer newPriority = 3;

        dto.setPriority(newPriority);

        assertEquals(newPriority, dto.getPriority());
    }

    @Test
    void setProject() {
        Project newProject = Project.builder()
                .title("Title")
                .build();

        dto.setProjectId(newProject.getId());

        assertEquals(newProject.getId(), dto.getProjectId());
    }

    @Test
    void setCategory() {
        Category newCategory = Category.builder()
                .title("Title")
                .project(project)
                .build();

        dto.setCategoryId(newCategory.getId());

        assertEquals(newCategory.getId(), dto.getCategoryId());
    }

    @Test
    void setTaskParent() {
        Task newTask = Task.builder()
                .title("Task title")
                .project(project)
                .build();

        dto.setParentId(newTask.getId());

        assertEquals(newTask.getId(), dto.getParentId());
    }

    @Test
    void setDateCreate() {
        LocalDate newDate = LocalDate.now();

        dto.setDateCreate(newDate);

        assertEquals(newDate,dto.getDateCreate());
    }

    @Test
    void testEquals() {
        TaskDTO dto1 = new TaskDTO();
        dto1.setTitle(title);
        dto1.setDescription(description);
        dto1.setPriority(priority);
        dto1.setStatus(status);
        dto1.setProjectId(project.getId());
        dto1.setCategoryId(category.getId());
        dto1.setParentId(taskParent.getId());
        dto1.setDateCreate(time);

        assertEquals(dto,dto1);
    }

    @Test
    void canEqual() {
        assertTrue(dto.canEqual(dto));
    }

    @Test
    void testHashCode() {
        assertNotEquals(0,dto.hashCode());
    }

    @Test
    void testToString() {
        assertNotNull(dto.toString());
    }

    @Test
    void builder() {
        assertEquals(title,dto.getTitle());
        assertEquals(description,dto.getDescription());
        assertEquals(priority,dto.getPriority());
        assertEquals(status,dto.getStatus());
        assertEquals(project.getId(),dto.getProjectId());
        assertEquals(category.getId(),dto.getCategoryId());
        assertEquals(taskParent.getId(),dto.getParentId());
        assertEquals(time,dto.getDateCreate());
    }
}