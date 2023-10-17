package com.example.backend.story.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProjectDTO {
    private String title;
    private String description;
    private List<Long> usersId;
}
