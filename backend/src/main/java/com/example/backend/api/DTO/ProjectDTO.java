package com.example.backend.api.DTO;

import lombok.Data;

@Data
public class ProjectDTO {
    private String title;
    private String description;
    private Long userId;
}
