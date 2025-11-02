package com.example.demo.services;

import org.springframework.stereotype.Service;

import com.example.demo.domain.model.Users;
import com.example.demo.exceptions.DuplicateException;
import com.example.demo.infra.repositories.UserRepository;
import com.example.demo.shared.dto.request.SignupRequest;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder){
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void signup(SignupRequest request){
        String email = request.email();
        Optional<Users> existingUser = repository.findByEmail(email);

        if(existingUser.isPresent()){
            throw new DuplicateException(String.format("User with the email address '%s' already exists", email));
        }

        String hashedPassword = passwordEncoder.encode(request.password());
        Users user = new Users(request.email(), hashedPassword, request.fullName(), LocalDateTime.now());
        repository.add(user);
    }

    public Optional<String> getFullNameFromUser(String email){
        
        if(email != null && !email.isEmpty()){
          return repository.getFullNameByEmail(email);  
        }
        return Optional.empty();
        
    }
}
