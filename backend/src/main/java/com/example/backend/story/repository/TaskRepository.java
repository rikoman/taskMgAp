package com.example.backend.story.repository;

import com.example.backend.story.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
     Page<Task> findByProjectIdAndStatusTrue(Long id, PageRequest pageRequest);

     Page<Task> findByCategoryIdAndStatusTrue(Long id, PageRequest pageRequest);

     Page<Task> findByStatusFalse(PageRequest pageRequest);

}
