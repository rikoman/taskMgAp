package com.example.backend.api.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaskDTO {
    private String title;
    private Boolean status;
    private String description;
    private Integer priority;
    private Long projectId;
    private Long categoryId;
    private String date;
    private Long parentId;
}
