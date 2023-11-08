package com.example.backend.story.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CommentTest {

    Long id = 1l;

    String content = "content";

    Project project = Project.builder()
            .title("Project Title")
            .build();

    Category category = Category.builder()
            .title("category Title")
            .project(project)
            .build();

    Task task = Task.builder()
            .title("title")
            .project(project)
            .build();

    Comment comment;

    LocalDateTime time = LocalDateTime.now();

    @BeforeEach
    void prepareDate(){
        comment = Comment.builder()
                .id(id)
                .content(content)
                .project(project)
                .category(category)
                .task(task)
                .datePublication(time)
                .build();
    }

    @Test
    void getId() {
        assertEquals(id,comment.getId());
    }

    @Test
    void getContent() {
        assertEquals(content,comment.getContent());
    }

    @Test
    void getTask() {
        assertEquals(task,comment.getTask());
    }

    @Test
    void getProject() {
        assertEquals(project,comment.getProject());
    }

    @Test
    void getCategory() {
        assertEquals(category,comment.getCategory());
    }

    @Test
    void getDatePublication() {
        assertEquals(time,comment.getDatePublication());
    }

    @Test
    void setId() {
        Long newId = 2L;

        comment.setId(newId);

        assertEquals(newId,comment.getId());
    }

    @Test
    void setContent() {
        String newContent = "Content text";

        comment.setContent(newContent);

        assertEquals(newContent,comment.getContent());
    }

    @Test
    void setTask() {
        Task newTask = Task.builder()
                .title("Task title")
                .project(project)
                .build();

        comment.setTask(newTask);

        assertEquals(newTask,comment.getTask());
    }

    @Test
    void setCategory() {
        Category newCategory = Category.builder()
                .title("Title")
                .project(project)
                .build();

        comment.setCategory(newCategory);

        assertEquals(newCategory,comment.getCategory());
    }

    @Test
    void setProject() {
        Project newProject = Project.builder()
                .title("Title")
                .build();

        comment.setProject(newProject);

        assertEquals(newProject,comment.getProject());
    }

    @Test
    void setDatePublication() {
        LocalDateTime newDate = LocalDateTime.now();

        comment.setDatePublication(newDate);

        assertEquals(newDate,comment.getDatePublication());
    }

    @Test
    void testEquals() {
        Comment comment1 = Comment.builder()
                .id(id)
                .content(content)
                .task(task)
                .category(category)
                .project(project)
                .datePublication(time)
                .build();

        assertEquals(comment, comment1);
    }

    @Test
    void canEqual() {
        assertTrue(comment.canEqual(comment));
    }

    @Test
    void testHashCode() {
        assertNotEquals(0,comment.hashCode());
    }

    @Test
    void testToString() {
        assertNotNull(comment.toString());
    }

    @Test
    void builder() {
        assertEquals(id,comment.getId());
        assertEquals(content,comment.getContent());
        assertEquals(task,comment.getTask());
        assertEquals(project,comment.getProject());
        assertEquals(category,comment.getCategory());
        assertEquals(time,comment.getDatePublication());
    }
}