package com.example.backend.story.repository;

import com.example.backend.story.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByProjectId(Long id);

    List<Comment> findByCategoryId(Long id);

    List<Comment> findByTaskId(Long id);
}