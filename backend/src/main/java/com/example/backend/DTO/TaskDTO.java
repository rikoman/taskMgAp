package com.example.backend.DTO;

import lombok.Data;

@Data
public class TaskDTO {
    private String title;
    private Boolean status;
    private String description;
    private Integer priority;
}
