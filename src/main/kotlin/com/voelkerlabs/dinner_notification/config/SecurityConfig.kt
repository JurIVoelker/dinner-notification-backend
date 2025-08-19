package com.voelkerlabs.dinner_notification.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeHttpRequests { requests -> requests.anyRequest().authenticated() }
        http.sessionManagement { session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)}
        http.httpBasic(Customizer.withDefaults())
        http.csrf{csrfConfigurer -> csrfConfigurer.disable()}
        return http.build()
     }
}