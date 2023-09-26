package com.example.backend.DTO;

import com.example.backend.entity.user.User;
import lombok.Data;

@Data
public class TaskDTO {
    private String title;
    private Boolean status;
    private String description;
    private Integer priority;
    private User user;
    private String date;
}
