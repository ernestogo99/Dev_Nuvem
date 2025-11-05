package com.example.demo.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.demo.domain.model.Users;
import com.example.demo.infra.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    private final UserRepository repository;

    public UserDetailsServiceImpl(UserRepository repository){
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email){
        Users user = repository.findByEmail(email).orElseThrow(() ->
            new UsernameNotFoundException(String.format("User does not exist, email: %s", email))
        );
        
        return org.springframework.security.core.userdetails.User.builder()
            .username(user.getEmail())
            .password(user.getPassword())
            .build();
    }

    
}
