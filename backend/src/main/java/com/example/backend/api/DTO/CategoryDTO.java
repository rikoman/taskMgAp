package com.example.backend.api.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@NoArgsConstructor
public class CategoryDTO {
    @NotNull
    private String title;
    private String description;
    @NotNull
    private Long projectId;
}