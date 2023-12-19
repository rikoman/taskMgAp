package com.example.backend.story.repository;

import com.example.backend.story.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {

    Page<Project> findByUsersId(Long id, PageRequest pageRequest);
}
