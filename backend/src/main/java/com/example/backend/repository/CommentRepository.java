package com.example.backend.repository;

import com.example.backend.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByProjectId(Long id);

    List<Comment> findByCategoryId(Long id);

    List<Comment> findByTaskId(Long id);
}