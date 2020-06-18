package com.binus.project.forumuaa.config.security;
import com.binus.project.forumuaa.entity.UserAccount;
import com.binus.project.forumuaa.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service("userDetailsService")
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }




    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        UserAccount userAccount = null;
        try {
            Optional<UserAccount> user = userRepository.findByName(username);
            return new SecurityUser(user.get());
        } catch (Exception e) {
            e.printStackTrace();
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }
    }
}
