package com.example.pcs.PCS.config;

import com.example.pcs.PCS.util.JWTFilter;
import com.example.pcs.PCS.util.JWTUtil;
import com.example.pcs.PCS.util.LoginFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil) {

        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf((auth) -> auth.disable()) // CSRF 보호 비활성화
                .formLogin((auth) -> auth.disable())// 폼 기반 로그인 비활성화
                .httpBasic((auth) -> auth.disable()) // HTTP 기본 인증 비활성화
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/","/findid", "/findpwd","/contract/execute","/check/verify","/contract", "/check","/about","/member","/login", "/signup", "/register", "/log", "/css/**", "/js/**","/image/**", "/favicon.ico").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class) // JWT Filter before UsernamePasswordAuthenticationFilter
                .addFilter(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil)); // Add LoginFilter appropriately
        return http.build();
    }
}

