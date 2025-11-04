package com.example.demo.infra.repositories;

import java.util.List;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.example.demo.domain.model.LoginAttempt;

@Repository
public class LoginAttemptRepository {

    private static final int RECENT_COUNT = 10;
    private static final String INSERT = "INSERT INTO public.login_attempt (email, success, created_at) VALUES(:email, :success, :createdAt)";
    private static final String FIND_RECENT = "SELECT * FROM public.login_attempt WHERE email = :email ORDER BY created_at DESC LIMIT :recentCount";

    private final JdbcClient jdbcClient;

    public LoginAttemptRepository(JdbcClient jdbcClient){
        this.jdbcClient = jdbcClient;
    }

    public void add(LoginAttempt loginAttempt){
        long affected = jdbcClient.sql(INSERT)
            .param("email", loginAttempt.getEmail())
            .param("success", loginAttempt.isSuccess())
            .param("createdAt", loginAttempt.getCreatedAt())
            .update();

        Assert.isTrue(affected == 1, "Could not add login attempt.");    
    }

    public List<LoginAttempt> findRecent(String email){
        return jdbcClient.sql(FIND_RECENT)
            .param("email", email)
            .param("recentCount", RECENT_COUNT)
            .query(LoginAttempt.class)
            .list();
    }
}
