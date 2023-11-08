package com.example.backend.story.DTO;

import com.example.backend.story.entity.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProjectDTOTest {

    private final String title = "title";

    private final String description = "description";

    private final ProjectDTO dto = new ProjectDTO();

    private final List<Long> users = new ArrayList<>();

    @BeforeEach
    void prepareDate(){
        users.add(new User("Roman","dhjsfbjhbf","dsaf@sdf.com").getId());
        users.add(new User("Romasdan","dhjsfbjhaaabf","dsaf@dddsdf.com").getId());
        users.add(new User("Romssan","dhjsfbasdjhbf","dsaf@sasddf.com").getId());

        dto.setTitle(title);
        dto.setDescription(description);
        dto.setUsersId(users);
    }

    @Test
    void getTitle() {
        assertEquals(title,dto.getTitle());
    }

    @Test
    void getDescription() {
        assertEquals(description,dto.getDescription());
    }

    @Test
    void getUser() {
        assertEquals(users,dto.getUsersId());
    }

    @Test
    void setTitle() {
        String title = "Home work";

        dto.setTitle(title);

        assertEquals(title,dto.getTitle());
    }

    @Test
    void setDescription() {
        String description = "bad businnes";

        dto.setDescription(description);

        assertEquals(description,dto.getDescription());
    }

    @Test
    void setUser() {
        List<Long> newUser = new ArrayList<>();

        newUser.add(new User("Roman","dhjsfbjhbf","dsaf@sdf.com").getId());

        dto.setUsersId(newUser);

        assertEquals(newUser,dto.getUsersId());
    }

    @Test
    void testEquals() {
        ProjectDTO dto1 = new ProjectDTO();

        dto1.setTitle(title);
        dto1.setDescription(description);
        dto1.setUsersId(users);

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
}