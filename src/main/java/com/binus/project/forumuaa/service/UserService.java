package com.binus.project.forumuaa.service;


import com.binus.project.forumuaa.entity.Role;
import com.binus.project.forumuaa.entity.UserAccount;
import com.binus.project.forumuaa.exception.ForumApplicationException;
import com.binus.project.forumuaa.exception.InvalidRequestException;
import com.binus.project.forumuaa.exception.RecordNotFoundException;
import com.binus.project.forumuaa.model.ChangePasswordRequest;
import com.binus.project.forumuaa.repository.RoleRepository;
import com.binus.project.forumuaa.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;

        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public Optional<UserAccount> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<UserAccount> getUserByName(String name) {
        return userRepository.findByName(name);
    }

    public UserAccount createUser(UserAccount userAccount) {
        if (userRepository.existsByName(userAccount.getName())) {
            throw new InvalidRequestException("Username is already in use");
        }
        userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
        Optional<Role> role_user = roleRepository.findByName("ROLE_USER");
        userAccount.setRoles(new HashSet<>(Collections.singletonList(role_user.orElse(null))));
        return userRepository.save(userAccount);
    }

    public UserAccount updateUser(UserAccount userAccount) {
        Optional<UserAccount> byId = userRepository.findById(userAccount.getId());
        if (!byId.isPresent()) {
            throw new ForumApplicationException("User with id ${user.id} not found");
        }
        userAccount.setPassword(byId.get().getPassword());
        userAccount.setRoles(byId.get().getRoles());
        return userRepository.save(userAccount);

    }

    public void deleteUser(Long userId) {
        Optional<UserAccount> byId = userRepository.findById(userId);
        byId.ifPresent(userRepository::delete);
    }

    public List<UserAccount> getAllUsers() {
        return  userRepository.findAll();
    }

    public void changePassword(String name, ChangePasswordRequest changePasswordRequest) {
        Optional<UserAccount> user = this.getUserByName(name);
        if (!user.isPresent()) {
            throw new RecordNotFoundException("User with name not found");
        }
        UserAccount userAccount = user.get();
        if (passwordEncoder.matches(changePasswordRequest.getOldPassword(), userAccount.getPassword())) {
            userAccount.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
            userRepository.save(userAccount);
        } else {
            throw new InvalidRequestException("current and old password do not match");
        }
    }
}
