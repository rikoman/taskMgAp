package com.example.backend.story.repository;

import com.example.backend.story.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

    List<Task> findByStatusTrue();

    List<Task> findByStatusTrueAndPriority(Integer id);

    List<Task> findByDateAndStatusTrue(String date);

    List<Task> findByCategoryId(Long id);

    List<Task> findByProjectId(Long id);
}
