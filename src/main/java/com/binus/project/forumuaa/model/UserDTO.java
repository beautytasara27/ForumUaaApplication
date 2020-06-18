package com.binus.project.forumuaa.model;

import com.binus.project.forumuaa.entity.Role;
import com.binus.project.forumuaa.entity.UserAccount;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class UserDTO {
    private Long id;

    private String name;

    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private List<String> roles;

    private String profilePicture;

    public UserAccount toEntity() {
        UserAccount userAccount = new UserAccount();
        userAccount.setId(this.id);
        userAccount.setName(this.name);
        userAccount.setEmail(this.email);
        userAccount.setPassword(this.password);
        return userAccount;
    }

    public static UserDTO fromEntity(UserAccount userAccount) {
        UserDTO dto = new UserDTO();
        dto.setId(userAccount.getId());
        dto.setName(userAccount.getName());
        dto.setEmail(userAccount.getEmail());
        dto.setPassword(userAccount.getPassword());
        dto.setEmail(userAccount.getProfilePicture());
        dto.setRoles(userAccount.getRoles().stream().map(Role::getName).collect(Collectors.toList()));

        return dto;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }


}
