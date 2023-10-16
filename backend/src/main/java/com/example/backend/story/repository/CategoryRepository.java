package com.example.backend.story.repository;

import com.example.backend.story.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    List<Category> findByProjectId(Long id);

    List<Category> findByCorporateProjectId(Long id);
}
