package com.example.backend.story.DTO;

import com.example.backend.story.entity.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryDTOTest {

    private final Long id = 1l;

    private final String title = "title";

    private final String description = "description";

    private final Project project = Project.builder()
            .title("Project Title")
            .build();

    private CategoryDTO dto;

    @BeforeEach
    void prepareDate(){
        dto = CategoryDTO.builder()
                .title(title)
                .description(description)
                .projectId(project.getId())
                .build();
    }

    @Test
    void getTitle() {
        assertEquals(title, dto.getTitle());
    }

    @Test
    void getDescription() {
        assertEquals(description,dto.getDescription());
    }

    @Test
    void getProject() {
        assertEquals(project.getId(), dto.getProjectId());
    }

    @Test
    void setTitle() {
        String newTitle = "отложить";

        dto.setTitle(newTitle);

        assertEquals(newTitle, dto.getTitle());
    }

    @Test
    void setDescription() {
        String newDescription = "Выполнить потом";

        dto.setDescription(newDescription);

        assertEquals(newDescription,dto.getDescription());
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
        CategoryDTO dto1 = CategoryDTO.builder()
                .title(title)
                .description(description)
                .projectId(project.getId())
                .build();

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

    @Test
    void builder() {
        assertEquals(title, dto.getTitle());
        assertEquals(description,dto.getDescription());
        assertEquals(project.getId(),dto.getProjectId());
    }
}