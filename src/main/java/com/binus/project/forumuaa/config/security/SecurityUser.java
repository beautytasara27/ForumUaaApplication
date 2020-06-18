package com.binus.project.forumuaa.config.security;


import com.binus.project.forumuaa.entity.UserAccount;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.stream.Collectors;

public class SecurityUser extends org.springframework.security.core.userdetails.User {
    private UserAccount userAccount;
    public SecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public SecurityUser(UserAccount userAccount) {
        super(userAccount.getName(), userAccount.getPassword(),
                userAccount.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()));
        this.userAccount = userAccount;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }
}
