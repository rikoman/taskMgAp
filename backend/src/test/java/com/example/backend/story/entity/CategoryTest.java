package com.example.backend.story.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    Long id = 1l;

    String title = "title";

    String description = "description";

    Project project = Project.builder()
            .title("Project Title")
            .build();

    Category category;

    @BeforeEach
    void prepareDate(){
        category = Category.builder()
                .id(id)
                .title(title)
                .description(description)
                .project(project)
                .build();
    }

    @Test
    void getId() {
        assertEquals(id,category.getId());
    }

    @Test
    void getTitle() {
        assertEquals(title, category.getTitle());
    }

    @Test
    void getDescription() {
        assertEquals(description,category.getDescription());
    }

    @Test
    void getProject() {
        assertEquals(project, category.getProject());
    }

    @Test
    void setId() {
        Long newId = 2l;

        category.setId(newId);

        assertEquals(newId,category.getId());
    }

    @Test
    void setTitle() {
        String newtitle = "отложить";

        category.setTitle(newtitle);

        assertEquals(newtitle, category.getTitle());
    }

    @Test
    void setDescription() {
        String newDescription = "Выполнить потом";

        category.setDescription(newDescription);

        assertEquals(newDescription,category.getDescription());
    }

    @Test
    void setProject() {
        Project newProject = Project.builder()
                .title("Title")
                .build();

        category.setProject(newProject);

        assertEquals(newProject,category.getProject());
    }
    @Test
    void testEquals() {
        Category  category1 = Category.builder()
                .id(id)
                .title(title)
                .description(description)
                .project(project)
                .build();

        assertEquals(category,category1);
    }

    @Test
    void canEqual() {
        assertTrue(category.canEqual(category));
    }

    @Test
    void testHashCode() {
        assertNotEquals(0,category.hashCode());
    }

    @Test
    void testToString() {
        assertNotNull(category.toString());
    }

    @Test
    void builder() {
        assertEquals(id,category.getId());
        assertEquals(title, category.getTitle());
        assertEquals(description,category.getDescription());
        assertEquals(project,category.getProject());
    }
}