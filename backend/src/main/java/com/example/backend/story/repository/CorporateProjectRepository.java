package com.example.backend.story.repository;

import com.example.backend.story.entity.CorporateProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CorporateProjectRepository extends JpaRepository<CorporateProject, Long> {
    List<CorporateProject> findByUsersId(Long id);
}