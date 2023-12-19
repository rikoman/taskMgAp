package com.example.backend.story.DTO;

import com.example.backend.story.enums.Priority;
import com.example.backend.story.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class TaskDTO {
    private String title;
    private Status status;
    private String description;
    private Priority priority;
    private Long projectId;
    private Long categoryId;
    private Long parentId;
    private LocalDate dateCreate;
}
