package com.example.shopappbackend.security;

import com.example.shopappbackend.jwt.JwtAuthenticationFilter;
import com.example.shopappbackend.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Value("${api.prefix}")
    private String apiPrefix;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> {
                    request.requestMatchers(HttpMethod.POST, String.format("%s/users/login", apiPrefix)).permitAll();
                    request.requestMatchers(HttpMethod.POST, String.format("%s/users/register", apiPrefix)).permitAll();
                    request.requestMatchers(HttpMethod.GET, String.format("%s/users/**", apiPrefix)).hasAnyRole(Role.ADMIN);
                    request.requestMatchers(HttpMethod.PUT, String.format("%s/users/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.USER);
                    request.requestMatchers(HttpMethod.DELETE, String.format("%s/users/**", apiPrefix)).hasAnyRole(Role.ADMIN);
                    request.requestMatchers(HttpMethod.GET, String.format("%s/categories/**", apiPrefix)).permitAll();
                    request.requestMatchers(HttpMethod.POST, String.format("%s/categories/**", apiPrefix)).hasAnyRole(Role.ADMIN);
                    request.requestMatchers(HttpMethod.PUT, String.format("%s/categories/**", apiPrefix)).hasAnyRole(Role.ADMIN);
                    request.requestMatchers(HttpMethod.DELETE, String.format("%s/categories/**", apiPrefix)).hasAnyRole(Role.ADMIN);
                    request.requestMatchers(HttpMethod.GET, String.format("%s/products/**", apiPrefix)).permitAll();
                    request.requestMatchers(HttpMethod.POST, String.format("%s/products/**", apiPrefix)).hasAnyRole(Role.ADMIN);
                    request.requestMatchers(HttpMethod.PUT, String.format("%s/products/**", apiPrefix)).hasAnyRole(Role.ADMIN);
                    request.requestMatchers(HttpMethod.DELETE, String.format("%s/products/**", apiPrefix)).hasAnyRole(Role.ADMIN);
                    request.requestMatchers(HttpMethod.GET, String.format("%s/orders/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.USER);
                    request.requestMatchers(HttpMethod.POST, String.format("%s/orders/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.USER);
                    request.requestMatchers(HttpMethod.PUT, String.format("%s/orders/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.USER);
                    request.requestMatchers(HttpMethod.DELETE, String.format("%s/orders/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.USER);
                    request.requestMatchers(HttpMethod.GET, String.format("%s/order_details/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.USER);
                    request.requestMatchers(HttpMethod.POST, String.format("%s/order_details/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.USER);
                    request.requestMatchers(HttpMethod.PUT, String.format("%s/order_details/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.USER);
                    request.requestMatchers(HttpMethod.DELETE, String.format("%s/order_details/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.USER);
                    request.anyRequest().authenticated();
                })
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
