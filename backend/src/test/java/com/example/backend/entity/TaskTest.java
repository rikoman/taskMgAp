package com.example.backend.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    Task task;
    @BeforeEach
    void prepareData(){
        task = Task.builder()
                .id(1l)
                .title("buy milk")
                .description("buy milk in supermarket")
                .priority(3)
                .status(true)
                .build();
    }

    @Test
    void getId() {
        assertEquals(1,task.getId());
    }

    @Test
    void getTitle() {
        assertEquals("buy milk",task.getTitle());
    }

    @Test
    void getStatus() {
        assertEquals(true,task.getStatus());
    }

    @Test
    void getDescription() {
        assertEquals("buy milk in supermarket",task.getDescription());
    }

    @Test
    void getPriority() {
        assertEquals(3,task.getPriority());
    }

    @Test
    void getProject() {
    }

    @Test
    void getCategory() {
    }

    @Test
    void getUser() {
    }

    @Test
    void setId() {
        task.setId(2l);
        assertEquals(2,task.getId());
    }

    @Test
    void setTitle() {
        task.setTitle("buy car");
        assertEquals("buy car",task.getTitle());
    }

    @Test
    void setStatus() {
        task.setStatus(false);
        assertEquals(false,task.getStatus());
    }

    @Test
    void setDescription() {
        task.setDescription("buy milk in other city");
        assertEquals("buy milk in other city",task.getDescription());
    }

    @Test
    void setPriority() {
        task.setPriority(2);
        assertEquals(2,task.getPriority());
    }

    @Test
    void setProject() {
    }

    @Test
    void setCategory() {
    }

    @Test
    void setUser() {
    }

    @Test
    void testEquals() {
        Task task = Task.builder()
                .id(1l)
                .title("buy milk")
                .description("buy milk in supermarket")
                .priority(3)
                .status(true)
                .build();
        assertTrue(this.task.equals(task));
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
        assertEquals(1,task.getId());
        assertEquals("buy milk",task.getTitle());
        assertEquals("buy milk in supermarket",task.getDescription());
        assertEquals(3,task.getPriority());
        assertEquals(true,task.getStatus());
    }
}