package com.binus.project.forumuaa.web;


import com.binus.project.forumuaa.entity.UserAccount;
import com.binus.project.forumuaa.model.ChangePasswordRequest;
import com.binus.project.forumuaa.model.CreateUserRequest;
import com.binus.project.forumuaa.model.UserDTO;
import com.binus.project.forumuaa.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(UserDTO::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    @ResponseStatus(CREATED)
    public UserDTO createUser(@RequestBody CreateUserRequest createUserRequest) {
        UserAccount userAccount = new UserAccount();
        userAccount.setEmail(createUserRequest.getEmail());
        userAccount.setName(createUserRequest.getName());
        userAccount.setPassword(createUserRequest.getPassword());
        userAccount.setProfilePicture(createUserRequest.getProfilePicture());
        return UserDTO.fromEntity(userService.createUser(userAccount));

    }


    @GetMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> getAllUser() {

      return   userService.getAllUsers().stream().map(UserDTO::fromEntity).collect(Collectors.toList());

    }

    @DeleteMapping("/{userId}")
    //@PreAuthorize("hasRole('ADMIN')")
    public String deleteUser(@PathVariable Long userId) {
         userService.deleteUser(userId);
         return  "user deleted";
    }


    @PostMapping("/change-password")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        String name = currentUser.getName();
        userService.changePassword(name, changePasswordRequest);
        return "password changed";
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('ROLE_USER')")
    public UserDTO me() {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        String name = currentUser.getName();
        return UserDTO.fromEntity( userService.getUserByName(name).get());
    }
}
