package com.example.demo.controller;

import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.security.jwt.JwtHelper;
import com.example.demo.services.LoginService;
import com.example.demo.services.UserService;
import com.example.demo.shared.dto.request.SignupRequest;
import com.example.demo.shared.dto.response.LoginResponse;
import com.example.demo.shared.dto.request.LoginRequest;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final LoginService loginService;
    private final JwtHelper jwtHelper;

    public AuthenticationController(AuthenticationManager authenticationManager, UserService userService, LoginService loginService, JwtHelper jwtHelper) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.loginService = loginService;
        this.jwtHelper = jwtHelper;
    }


    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@Valid @RequestBody SignupRequest requestDto, BindingResult bindingResult) throws BadRequestException{
        if(bindingResult.hasErrors()){
            throw new BadRequestException("Register failed. Please ensure that your credentials are correct and that all required fields are filled out properly.", bindingResult);
        }
        userService.signup(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) throws BadRequestException{
        if (bindingResult.hasErrors()) {
            throw new BadRequestException("Login failed. Please ensure that your credentials are correct and that all required fields are filled out properly.", bindingResult);
        }
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));        
        } catch( BadCredentialsException e){
            loginService.addLoginAttempt(loginRequest.email(), false);
            throw e;
        }

        Optional<String> fullName = userService.getFullNameFromUser(loginRequest.email());

        if(fullName.isPresent()){
          String token = jwtHelper.generateToken(loginRequest.email(), fullName.get());  
          loginService.addLoginAttempt(loginRequest.email(), true);
          return ResponseEntity.ok(new LoginResponse(loginRequest.email(), token));
        }
        else{
            loginService.addLoginAttempt(loginRequest.email(), false);
            throw new NotFoundException("User not found.");
        }

        
    }


}
