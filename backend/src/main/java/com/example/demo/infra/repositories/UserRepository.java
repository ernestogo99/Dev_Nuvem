package com.example.demo.infra.repositories;

import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.example.demo.domain.model.User;

@Repository
public class UserRepository{
   private static final String INSERT = "INSERT INTO public.user (full_name, email, password, role) VALUES(:full_name, :email, :password, :role)";
   private static final String FIND_BY_EMAIL = "SELECT * FROM public.user WHERE email = :email";
   private static final String GET_FULLNAME = "SELECT full_name FROM public.user WHERE email = :email";

   private final JdbcClient jdbcClient;

   public UserRepository(JdbcClient jdbcClient){
    this.jdbcClient = jdbcClient;
   }

   public void add(User user){
        long affected = jdbcClient.sql(INSERT)
            .param("full_name", user.getFullName())
            .param("email", user.getEmail())
            .param("password", user.getPassword())
            .param("role", user.getRole())
            .update();

        Assert.isTrue(affected == 1, "Could not add user.");   
    }

   public Optional<User> findByEmail(String email){
        return jdbcClient.sql(FIND_BY_EMAIL)
        .param("email", email)
        .query(User.class)
        .optional();
   }

   public Optional<String> getFullNameByEmail(String email){
    return jdbcClient.sql(GET_FULLNAME)
        .param("email", email)
        .query( String.class)
        .optional();
   }

}
