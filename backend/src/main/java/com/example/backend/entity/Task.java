package com.example.backend.entity;

import com.example.backend.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private Boolean status;
    private String description;
    private Integer priority;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String date;
}