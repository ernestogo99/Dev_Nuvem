package com.example.demo.domain.model;



import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "users")
public class Users {

    public Users(String email, String password, String fullName, LocalDateTime createdAt){
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.createdAt = createdAt;
    }   

    public Users(){}
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 128, nullable = false, unique = true)
    @NotEmpty(message = "Email is required")
    @NotNull(message = "Email should not be null")
    @Email(message = "Invalid email format")
    private String email;

    @Column(length = 64, nullable = false)
    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "Password must contain at least one alphabetical character, one digit, one special character, and be at least 6 characters long.")
    private String password;

    @Column(length = 32, nullable = false)
    private String role = "ROLE_ADMIN";

    @Column(name= "full_name", length = 256, nullable = false)
    @NotBlank(message = "Name is required")
    private String fullName;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;



    public Long getId() {return id;}
    public void setId(Long id) { this.id = id;}

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getFullName(){return fullName;};
    public void setFullName(String fullName){this.fullName = fullName;};
}
