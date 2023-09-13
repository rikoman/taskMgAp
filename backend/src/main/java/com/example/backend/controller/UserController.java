package com.example.backend.controller;

import com.example.backend.DTO.UserDTO;
import com.example.backend.entity.User;
import com.example.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> create(UserDTO dto){
        User user = User.builder()
                .name(dto.getName())
                .build();
        return new ResponseEntity<>(userService.createUser(dto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> readAll(){
        return new ResponseEntity<>(userService.readAllUser(),HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<User> update(@RequestBody User user){
        return new ResponseEntity<>(userService.updateUser(user),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Long id){
        userService.deleteUser(id);
        return HttpStatus.OK;
    }
}
