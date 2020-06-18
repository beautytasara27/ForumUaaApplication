package com.binus.project.forumuaa.config;


import com.binus.project.forumuaa.entity.Role;
import com.binus.project.forumuaa.entity.UserAccount;
import com.binus.project.forumuaa.repository.RoleRepository;
import com.binus.project.forumuaa.repository.UserRepository;
import com.binus.project.forumuaa.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;


@Slf4j
@Component
public class DataInitializer implements CommandLineRunner {

    private final static Logger LOGGER = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;


    void createRoles() {
        Role admin = roleRepository.findByName("ADMIN").orElse(null);
        if (admin == null) {
            Set<Role> roles = new HashSet<>();
            roles.add(new Role("ADMIN"));
            roles.add(new Role("ROLE_USER"));
            roles.forEach((role) -> {
                roleRepository.save(role);
            });

        }

    }

    public UserAccount createUser() {
        UserAccount userAccount = new UserAccount();
        userAccount.setEmail("admin@gmail.com");
        userAccount.setName("admin");
        userAccount.setPassword("password@123");
        return userAccount;
    }

    void createSuperUser() {
        UserAccount admin = userRepository.findByEmail("admin@gmail.com").orElse(null);
        Set<Role> roles = new HashSet<>(roleRepository.findAll());
        if (admin == null) {
            UserAccount userAccount = createUser();
            userAccount.setRoles(roles);
            userService.createUser(userAccount);
        }
    }

    @Override
    public void run(String... args) {
        LOGGER.info("Running application initializer");
        createRoles();
        createSuperUser();
    }
}