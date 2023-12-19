package com.example.backend.story.repository;

import com.example.backend.story.entity.Task;
import com.example.backend.story.enums.Priority;
import com.example.backend.story.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

     Page<Task> findByProjectIdAndStatusAndPriority(Long id, Status status, Priority priority, PageRequest pageRequest);

     Page<Task> findByCategoryIdAndStatusAndPriority(Long id, Status status, Priority priority, PageRequest pageRequest);

     Page<Task> findByStatusFalse(PageRequest pageRequest);
}
