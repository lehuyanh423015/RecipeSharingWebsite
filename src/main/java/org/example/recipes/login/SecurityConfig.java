package org.example.recipes.login;

import org.example.recipes.user.UserRepository;
import org.example.recipes.entity.Users;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // --- Add this bean ---
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepo) {
        return usernameOrEmail -> {
            // tìm bằng username hoặc email
            Users user = userRepo
                    .findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                    .orElseThrow(() ->
                            new UsernameNotFoundException("Không tìm thấy người dùng: " + usernameOrEmail));
            return User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRole())  // giả sử cột role chứa ROLE_USER
                    .build();
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login","/auth/register","/css/**","/js/**")
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login")
                        .defaultSuccessUrl("/recipes/new", true)
                        .failureUrl("/auth/login?error")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/auth/login?logout")
                        .permitAll()
                );
        return http.build();
    }
}