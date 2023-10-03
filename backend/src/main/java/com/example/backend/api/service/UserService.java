package com.example.backend.api.service;

import com.example.backend.api.exception.NotFoundException;
import com.example.backend.story.entity.user.User;
import com.example.backend.story.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User readUserById(Long id){
        return userRepository.findById(id).orElseThrow(()->new NotFoundException(String.format("User with %s id doesn' exist.",id)));
    }
}
