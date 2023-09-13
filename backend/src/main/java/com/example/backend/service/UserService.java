package com.example.backend.service;

import com.example.backend.DTO.UserDTO;
import com.example.backend.entity.User;
import com.example.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUser(@RequestBody UserDTO dto){
        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .build();
        return userRepository.save(user);
    }

    public List<User> readAllUser(){
        return userRepository.findAll();
    }

    public User updateUser (@RequestBody User user){
        return userRepository.save(user);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
