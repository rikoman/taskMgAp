package com.example.backend.story.repository;

import com.example.backend.story.enums.ERole;
import com.example.backend.story.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(ERole name);

    Boolean existsByName(ERole role);
}