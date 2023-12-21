package com.example.backend.api.controller;

import com.example.backend.api.service.ProjectService;
import com.example.backend.api.service.UserService;
import com.example.backend.story.entity.Category;
import com.example.backend.story.entity.Project;
import com.example.backend.story.enums.ERole;
import com.example.backend.story.entity.Role;
import com.example.backend.story.entity.User;
import com.example.backend.story.repository.CategoryRepository;
import com.example.backend.story.repository.ProjectRepository;
import com.example.backend.story.repository.RoleRepository;
import com.example.backend.story.repository.UserRepository;
import com.example.backend.security.config.jwt.JwtUtils;
import com.example.backend.security.pojo.JwtResponse;
import com.example.backend.security.pojo.LoginRequest;
import com.example.backend.security.pojo.MessageResponse;
import com.example.backend.security.pojo.SignupRequest;
import com.example.backend.security.service.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final ProjectRepository projectRepository;

    @Transactional
    @PostMapping("/signin")
    public ResponseEntity<?> authUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @Transactional
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is exist"));
        }

        User user = new User(signupRequest.getUsername(),passwordEncoder.encode(signupRequest.getPassword()),signupRequest.getEmail());

        Set<String> reqRoles = signupRequest.getRoles();
        Set<Role> roles = new HashSet<>();
        System.out.println(reqRoles+" "+roles+" "+signupRequest.getRoles()+" "+signupRequest.getUsername());
        if (reqRoles == null) {
            Role userRole = roleRepository
                    .findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
            roles.add(userRole);
        } else {
            reqRoles.forEach(r -> {
                switch (r) {
                    case "admin":
                        Role adminRole = roleRepository
                                .findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error, Role ADMIN is not found"));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository
                                .findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error, Role MODERATOR is not found"));
                        roles.add(modRole);

                        break;

                    default:
                        Role userRole = roleRepository
                                .findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);
        createDefaultProjectAndCategories(user.getId());
        return ResponseEntity.ok(new MessageResponse("User CREATED"));
    }

    private void createDefaultProjectAndCategories(Long id){
        Project project = Project.builder()
                .title("Входящие")
                .description("Здесь отображаются простые задачи")
                .users(userService.readAllUserByIds(Collections.singletonList(id)))
                .build();

        Project savedProject = projectRepository.save(project);
        createCategory(savedProject.getId());
    }

    private void createCategory(Long id){
        Project existProject = projectRepository.findById(id).orElseThrow();
        Category categoryToDay = Category.builder()
                .title("Задачи на сегодня")
                .description("В данной категории будут отобразаться задачи предназначенные на текущую дату")
                .project(existProject)
                .build();

        Category categoryFuture = Category.builder()
                .title("Задачи на будущее")
                .description("В данной категории будут отобразаться задачи предназначенные на будущую дату")
                .project(existProject)
                .build();

        Category categoryOverDue = Category.builder()
                .title("Задачи на прошедшие дни")
                .description("В данной категории будут отобразаться задачи прошедших дней")
                .project(existProject)
                .build();

        categoryRepository.save(categoryToDay);
        categoryRepository.save(categoryFuture);
        categoryRepository.save(categoryOverDue);
    }
}