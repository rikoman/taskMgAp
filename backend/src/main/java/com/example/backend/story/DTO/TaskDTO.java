package com.example.backend.story.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class TaskDTO {
    private String title;
    private Boolean status;
    private String description;
    private Integer priority;
    private Long projectId;
    private Long categoryId;
    private Long parentId;
    private LocalDate dateCreate;
}
