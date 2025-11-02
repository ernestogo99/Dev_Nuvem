package com.example.demo.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.model.LoginAttempt;
import com.example.demo.infra.repositories.LoginAttemptRepository;


@Service
@Transactional(readOnly = true)
public class LoginService {
    
    private final LoginAttemptRepository repository;

    public LoginService(LoginAttemptRepository repository){
        this.repository = repository;
    }

    @Transactional
    public void addLoginAttempt(String email, boolean success){
        LoginAttempt loginAttempt = new LoginAttempt(email, success, LocalDateTime.now());
        repository.add(loginAttempt);
    }

    public List<LoginAttempt> findRecentLoginAttempts(String email){
        return repository.findRecent(email);
    }
}
