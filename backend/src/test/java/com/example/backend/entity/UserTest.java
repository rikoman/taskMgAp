package com.example.backend.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    User user;
    @BeforeEach
    void prepareDate(){
        user = User.builder()
                .id(1l)
                .name("Roman")
                .email("roman@gmail.com")
                .build();
    }

    @Test
    void getId() {
        assertEquals(1,user.getId());
    }

    @Test
    void getName() {
        assertEquals("Roman",user.getName());
    }

    @Test
    void getEmail() {
        assertEquals("roman@gmail.com",user.getEmail());
    }

    @Test
    void setId() {
        user.setId(2l);
        assertEquals(2,user.getId());
    }

    @Test
    void setName() {
        user.setName("Ivan");
        assertEquals("Ivan",user.getName());
    }

    @Test
    void setEmail() {
        user.setEmail("ivan@gmail.com");
        assertEquals("ivan@gmail.com",user.getEmail());
    }

    @Test
    void testEquals() {
        User user = User.builder()
                .id(1l)
                .name("Roman")
                .email("roman@gmail.com")
                .build();
        assertTrue(this.user.equals(user));
    }

    @Test
    void canEqual() {
        assertTrue(user.canEqual(user));
    }

    @Test
    void testHashCode() {
        assertNotEquals(0,user.hashCode());
    }

    @Test
    void testToString() {
        assertNotNull(user.toString());
    }

    @Test
    void builder() {
        assertEquals(1,user.getId());
        assertEquals("Roman",user.getName());
        assertEquals("roman@gmail.com",user.getEmail());
    }
}