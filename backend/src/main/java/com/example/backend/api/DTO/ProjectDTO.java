package com.example.backend.api.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@NoArgsConstructor
public class ProjectDTO {
    @NotNull
    private String title;
    private String description;
    @NotNull
    private Long userId;
}
