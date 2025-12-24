    package com.example.demo.security.configs;

    import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.http.HttpMethod;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
    import org.springframework.security.config.http.SessionCreationPolicy;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.demo.services.UserDetailsServiceImpl;

    @Configuration
    @EnableWebSecurity
    public class SecurityConfig {
        @Value("${front.url}")
        private String frontUrl;
        private final UserDetailsServiceImpl userDetailsServiceImpl;
        private final JwtAuthFilter jwtAuthFilter;

        public SecurityConfig(UserDetailsServiceImpl userDetailsServiceImpl, JwtAuthFilter jwtAuthFilter){
            this.userDetailsServiceImpl = userDetailsServiceImpl;
            this.jwtAuthFilter = jwtAuthFilter;
        }

        @Bean
        public PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception{
            return http
                .cors(cors-> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Set permissions on endpoints
                .authorizeHttpRequests( auth -> auth
                // public endpoints

                    .requestMatchers(HttpMethod.POST, "api/auth/signup/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "api/auth/login/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "api-docs/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "api/candies/**").permitAll()
                                .requestMatchers(
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html"
                                ).permitAll()

                //private endpoints
                    .anyRequest().authenticated()
                ).authenticationManager(authenticationManager)

                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
        }

          @Bean
        public CorsConfigurationSource corsConfigurationSource() {

            CorsConfiguration config = new CorsConfiguration();

            config.setAllowedOrigins(List.of(frontUrl));
            config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            config.setAllowCredentials(true);
            config.setAllowedHeaders(List.of("Authorization", "Content-Type"));

            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", config);
            return source;
        }

        @Bean
        public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception{
            AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
            authenticationManagerBuilder.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
            return authenticationManagerBuilder.build();
        }
    }
