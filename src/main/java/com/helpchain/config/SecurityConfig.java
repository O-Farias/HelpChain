package com.helpchain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Desabilitar CSRF para testes
            .authorizeHttpRequests()
            .requestMatchers("/api/**").permitAll() // Permitir todas as requisições para "/api/**"
            .anyRequest().authenticated(); // Para outros endpoints, exigir autenticação

        return http.build();
    }
}
