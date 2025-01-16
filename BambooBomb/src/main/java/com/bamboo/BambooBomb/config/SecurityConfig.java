package com.bamboo.BambooBomb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("securityFilterChain#####################");
        http
            // .authorizeHttpRequests(auth -> auth
            //     .requestMatchers("/login", "/login/**", "/swagger-ui/**").permitAll()  // 특정 경로 허용
            //     .anyRequest().authenticated()  // 다른 요청은 인증 필요
            // )
            .oauth2Login(Customizer.withDefaults());

        return http.build();
    }
}
