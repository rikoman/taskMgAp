package com.example.backend.DTO;

import com.example.backend.entity.Category;
import com.example.backend.entity.Project;
import lombok.Data;

@Data
public class TaskDTO {
    private String title;
    private Boolean status;
    private String description;
    private Integer priority;
    private Project project;
    private Category category;
    private String date;
}
