package com.example.backend.story.entity;

import com.example.backend.story.enums.Priority;
import com.example.backend.story.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String title;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String description;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonIdentityReference(alwaysAsId = true)
    private Project project;
    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIdentityReference(alwaysAsId = true)
    private Category category;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonIdentityReference(alwaysAsId = true)
    private Task parent;
    @JsonFormat(pattern = "yyyy-MM-dd") //   Переименовать
    private LocalDate dateCreate;
    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonIdentityReference(alwaysAsId = true)
    private User author;
}