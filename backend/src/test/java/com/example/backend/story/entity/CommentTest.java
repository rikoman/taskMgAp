package com.example.backend.story.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CommentTest {


    Long id = 1l;

    String content = "content";

    Project project = Project.builder()
            .id(1l)
            .title("Project Title")
            .build();

    Category category = Category.builder()
            .id(1l)
            .title("category Title")
            .project(project)
            .build();

    Task task = Task.builder()
            .id(1l)
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
                .task(task)
                .category(category)
                .project(project)
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
        comment.setId(2L);
        assertEquals(2l,comment.getId());
    }

    @Test
    void setContent() {
        comment.setContent("Content text");
        assertEquals("Content text",comment.getContent());
    }

    @Test
    void setTask() {
        Task newTask = Task.builder()
                .id(2L)
                .title("Task title")
                .project(project)
                .build();
        comment.setTask(newTask);
        assertEquals(newTask,comment.getTask());
    }

    @Test
    void setProject() {
        Project newProject = Project.builder()
                .id(2l)
                .title("Title")
                .build();
        comment.setProject(newProject);
        assertEquals(newProject,comment.getProject());
    }

    @Test
    void setCategory() {
        Category newCategory = Category.builder()
                .id(2l)
                .title("Title")
                .project(project)
                .build();
        comment.setCategory(newCategory);
        assertEquals(newCategory,comment.getCategory());
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
