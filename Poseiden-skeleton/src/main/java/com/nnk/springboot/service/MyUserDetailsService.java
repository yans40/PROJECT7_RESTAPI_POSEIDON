package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Slf4j
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUsername(username);

        if (user == null) {
            log.info("user null");
            throw new UsernameNotFoundException("Username not found" + username);
        } else {
            log.info("user finded");
            return new org.springframework.security.core.userdetails.User(user.getUsername(),
                    user.getPassword(),
                    Arrays.asList(new SimpleGrantedAuthority(user.getRole())));
        }

    }
}
