package com.example.homies.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.addAllowedOrigin("http://localhost:5173"); // Frontend origin
                    config.addAllowedMethod("*"); // Allow all HTTP methods
                    config.addAllowedHeader("*"); // Allow all headers
                    config.setAllowCredentials(true); // Allow cookies and authorization headers
                    return config;
                }))
//                .authorizeHttpRequests(authorize -> authorize
//                        .anyRequest().permitAll() // Allow all requests from everyone
//                )
//                .csrf(csrf -> csrf.disable()) // Disable CSRF for testing
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless sessions for APIs
//                );

//        return http.build();
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/register/**").permitAll() // Allow unauthenticated access
                                .requestMatchers("/index").permitAll() // Allow unauthenticated access
                                .requestMatchers("/users").hasRole("ADMIN") // Require ADMIN role for /users
                                .anyRequest().permitAll() // Allow all other requests for testing
                )
                .formLogin(form ->
                        form
                                .loginPage("/login") // Custom login page
                                .loginProcessingUrl("/login") // Login form action
                                .defaultSuccessUrl("/index", true) // Redirect to /index after login
                                .permitAll()
                )
                .logout(logout ->
                        logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // Logout URL
                                .logoutSuccessUrl("/index") // Redirect to /index after logout
                                .permitAll()
                )
                .csrf(csrf -> csrf.disable()); // Disable CSRF for testing in Postman

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}
