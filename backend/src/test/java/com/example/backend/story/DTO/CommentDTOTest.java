package com.example.backend.story.DTO;

import com.example.backend.story.entity.Category;
import com.example.backend.story.entity.Project;
import com.example.backend.story.entity.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CommentDTOTest {

    private final String content = "content";

    private final Project project = Project.builder()
            .title("Project Title")
            .build();

    private final Category category = Category.builder()
            .title("category Title")
            .project(project)
            .build();

    private final Task task = Task.builder()
            .title("title")
            .project(project)
            .build();

    private final CommentDTO dto = new CommentDTO();

    private final LocalDateTime time = LocalDateTime.now();

    @BeforeEach
    void prepareDate(){
        dto.setContent(content);
        dto.setProjectId(project.getId());
        dto.setCategoryId(category.getId());
        dto.setTaskId(task.getId());
    }

    @Test
    void getContent() {
        assertEquals(content, dto.getContent());
    }

    @Test
    void getTask() {
        assertEquals(task.getId(), dto.getTaskId());
    }

    @Test
    void getProject() {
        assertEquals(project.getId(), dto.getProjectId());
    }

    @Test
    void getCategory() {
        assertEquals(category.getId(), dto.getCategoryId());
    }

    @Test
    void setContent() {
        String newContent = "Content text";

        dto.setContent(newContent);

        assertEquals(newContent, dto.getContent());
    }

    @Test
    void setTask() {
        Task newTask = Task.builder()
                .title("Task title")
                .project(project)
                .build();

        dto.setTaskId(newTask.getId());

        assertEquals(newTask.getId(), dto.getTaskId());
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
    void setProject() {
        Project newProject = Project.builder()
                .title("Title")
                .build();

        dto.setProjectId(newProject.getId());

        assertEquals(newProject.getId(), dto.getProjectId());
    }

    @Test
    void testEquals() {
        CommentDTO dto1 = new CommentDTO();
        dto1.setContent(content);
        dto1.setProjectId(project.getId());
        dto1.setCategoryId(category.getId());
        dto1.setTaskId(task.getId());
        assertEquals(dto, dto1);
    }

    @Test
    void canEqual() {
        assertTrue(dto.canEqual(dto));
    }

    @Test
    void testHashCode() {
        assertNotEquals(0, dto.hashCode());
    }

    @Test
    void testToString() {
        assertNotNull(dto.toString());
    }
}