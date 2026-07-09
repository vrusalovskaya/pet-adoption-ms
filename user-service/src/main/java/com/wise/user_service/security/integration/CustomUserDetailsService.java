package com.wise.user_service.security.integration;

import com.wise.user_service.security.domain.SecurityUser;
import com.wise.user_service.user.domain.User;
import com.wise.user_service.user.exception.UserNotFoundException;
import com.wise.user_service.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) {
        try {
            User user = userService.findByEmail(email);
            return SecurityUser.from(user);
        } catch (UserNotFoundException ex) {
            throw new UsernameNotFoundException(ex.getMessage(), ex);
        }
    }
}
