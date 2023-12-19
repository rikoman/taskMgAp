package com.example.backend.story.repository;

import com.example.backend.story.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    Page<Comment> findByProjectId(Long id, PageRequest pageRequest);

    Page<Comment> findByCategoryId(Long id, PageRequest pageRequest);

    Page<Comment> findByTaskId(Long id, PageRequest pageRequest);
}