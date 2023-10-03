package com.example.backend.api.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@NoArgsConstructor
public class TaskDTO {
    @NotNull
    private String title;
    @NotNull
    private Boolean status;
    private String description;
    @NotNull
    private Integer priority;
    private Long projectId;
    private Long categoryId;
    private String date;
    private Long parentId;
}
