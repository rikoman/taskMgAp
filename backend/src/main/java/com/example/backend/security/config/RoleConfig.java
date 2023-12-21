package com.example.backend.security.config;

import com.example.backend.story.entity.Role;
import com.example.backend.story.enums.ERole;
import com.example.backend.story.repository.RoleRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleConfig implements InitializingBean {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        createRoles();
    }

    private void createRoles() {
        if (!roleRepository.existsByName(ERole.ROLE_USER)) {
            Role userRole = new Role();
            userRole.setName(ERole.ROLE_USER);
            roleRepository.save(userRole);
        }

        if (!roleRepository.existsByName(ERole.ROLE_ADMIN)) {
            Role adminRole = new Role();
            adminRole.setName(ERole.ROLE_ADMIN);
            roleRepository.save(adminRole);
        }
    }
}
