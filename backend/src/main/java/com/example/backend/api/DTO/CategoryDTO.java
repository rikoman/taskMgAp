package com.example.backend.api.DTO;

import lombok.Data;

@Data
public class CategoryDTO {
    private String title;
    private String description;
    private Long projectId;
}