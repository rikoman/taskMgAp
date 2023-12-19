package com.example.backend.story.repository;

import com.example.backend.story.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    Page<Category> findByProjectId(Long id, PageRequest pageRequest);
}
