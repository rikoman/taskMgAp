package com.example.backend.story.entity;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.io.Serializable;

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
    private Boolean status;
    private String description;
    private Integer priority;
    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonIdentityReference(alwaysAsId = true)
    private Project project;
    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIdentityReference(alwaysAsId = true)
    private Category category;
    private String date;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonIdentityReference(alwaysAsId = true)
    private Task parent;
}