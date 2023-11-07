package com.example.backend.story.entity;

import com.example.backend.story.entity.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {

    Long id = 1l;

    String title = "title";

    String description = "description";

    Project project;

    List<User> users= new ArrayList<>();

    @BeforeEach
    void prepareDate(){
        users.add(new User("Roman","dhjsfbjhbf","dsaf@sdf.com"));
        users.add(new User("Romasdan","dhjsfbjhaaabf","dsaf@dddsdf.com"));
        users.add(new User("Romssan","dhjsfbasdjhbf","dsaf@sasddf.com"));
        project = Project.builder()
                .id(id)
                .title(title)
                .description(description)
                .users(users)
                .build();
    }

    @Test
    void getId() {
       assertEquals(id,project.getId());
    }

    @Test
    void getTitle() {
        assertEquals(title,project.getTitle());
    }

    @Test
    void getDescription() {
        assertEquals(description,project.getDescription());
    }

    @Test
    void getUser() {
        assertEquals(users,project.getUsers());
    }

    @Test
    void setId() {
        Long id = 2l;
        project.setId(id);
        assertEquals(id,project.getId());
    }

    @Test
    void setTitle() {
        String title = "Home work";
        project.setTitle(title);
        assertEquals(title,project.getTitle());
    }

    @Test
    void setDescription() {
        String description = "bad businnes";
        project.setDescription(description);
        assertEquals(description,project.getDescription());
    }

    @Test
    void setUser() {
        List<User> newUser = List.of(new User("Roman","dhjsfbjhbf","dsaf@sdf.com"));
        project.setUsers(newUser);
        assertEquals(newUser,project.getUsers());
    }

    @Test
    void testEquals() {
        Project project1 = Project.builder()
                .id(id)
                .title(title)
                .description(description)
                .users(users)
                .build();
        assertEquals(project,project1);
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
        assertEquals(id,project.getId());
        assertEquals(title,project.getTitle());
        assertEquals(description,project.getDescription());
        assertEquals(users,project.getUsers());
    }
}