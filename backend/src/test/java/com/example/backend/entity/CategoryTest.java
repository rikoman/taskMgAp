package com.example.backend.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {
    Category category;
    @BeforeEach
    void prepareDate(){
        category = Category.builder()
                .id(1l)
                .title("Важно")
                .description("Категория для важных задач проекта")
                .build();
    }

    @Test
    void getId() {
        assertEquals(1,category.getId());
    }

    @Test
    void getTitle() {
        assertEquals("Важно", category.getTitle());
    }

    @Test
    void getDescription() {
        assertEquals("Категория для важных задач проекта",category.getDescription());
    }

    @Test
    void getProject() {
    }

    @Test
    void setId() {
        category.setId(2l);
        assertEquals(2,category.getId());
    }

    @Test
    void setTitle() {
        category.setTitle("Отложить");
        assertEquals("Отложить", category.getTitle());
    }

    @Test
    void setDescription() {
        category.setDescription("Выполнить потом");
        assertEquals("Выполнить потом",category.getDescription());
    }

    @Test
    void setProject() {
    }

    @Test
    void testEquals() {
        Category category = Category.builder()
                .id(1l)
                .title("Важно")
                .description("Категория для важных задач проекта")
                .build();
        assertTrue(this.category.equals(category));
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
        assertEquals(1,category.getId());
        assertEquals("Важно", category.getTitle());
        assertEquals("Категория для важных задач проекта",category.getDescription());
    }
}