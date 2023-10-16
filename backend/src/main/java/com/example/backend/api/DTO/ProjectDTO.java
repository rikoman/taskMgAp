package com.example.backend.api.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjectDTO {
    private String title;
    private String description;
    private Long userId;
}
