package com.example.backend.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {
    Project project;
    @BeforeEach
    void prepareDate(){
        project = Project.builder()
                .id(1l)
                .title("Business")
                .description("Good business")
                .status(false)
                .build();
    }

    @Test
    void getId() {
       assertEquals(1,project.getId());
    }

    @Test
    void getTitle() {
        assertEquals("Business",project.getTitle());
    }

    @Test
    void isStatus() {
        assertEquals(false,project.isStatus());
    }

    @Test
    void getDescription() {
        assertEquals("Good business",project.getDescription());
    }

    @Test
    void getUser() {
    }

    @Test
    void setId() {
        project.setId(2l);
        assertEquals(2,project.getId());
    }

    @Test
    void setTitle() {
        project.setTitle("Home work");
        assertEquals("Home work",project.getTitle());
    }

    @Test
    void setStatus() {
        project.setStatus(true);
        assertEquals(true,project.isStatus());
    }

    @Test
    void setDescription() {
        project.setDescription("bad business");
        assertEquals("bad business",project.getDescription());
    }

    @Test
    void setUser() {
    }

    @Test
    void testEquals() {
        Project project = Project.builder()
                .id(1l)
                .title("Business")
                .description("Good business")
                .status(false)
                .build();
        assertTrue(this.project.equals(project));
    }

    @Test
    void canEqual() {
        assertTrue(project.canEqual(project));
    }

    @Test
    void testHashCode() {
        assertNotEquals(0,project.hashCode());
    }

    @Test
    void testToString() {
        assertNotNull(project.toString());
    }

    @Test
    void builder() {
        assertEquals(1,project.getId());
        assertEquals("Business",project.getTitle());
        assertEquals(false,project.isStatus());
        assertEquals("Good business",project.getDescription());
    }
}