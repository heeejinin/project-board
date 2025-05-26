package com.example.project_board.config;

import com.example.project_board.jwt.JwtFilter;
import com.example.project_board.jwt.JwtUtil;
import com.example.project_board.jwt.LoginFilter;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class SecurityConfig {

  // AuthenticationManager가 인자로 받을 AuthenticationConfiguration 객체 생성자 주입
  private final AuthenticationConfiguration authenticationConfiguration;
  private final JwtUtil jwtUtil;

  // AuthenticationManager Bean 등록
  @ Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration configuration
  ) throws Exception {
    return configuration.getAuthenticationManager();
  }

  // 비밀번호 암호화
  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

    // csrf disable
    http
        .csrf((auth) -> auth.disable());

    // Form 로그인 방식 disable
    http
        .formLogin((auth) -> auth.disable());

    // http basic 인증 방식 disable
    http
        .httpBasic((auth) -> auth.disable());

    http
        .authorizeHttpRequests((auth) -> auth
            .requestMatchers("/login", "/", "/join").permitAll()
            .requestMatchers("/admin").hasRole("ADMIN")
            .anyRequest().authenticated()
        );

    // JwtFilter 등록 -> 로그인 전에 filter 지정
    http
        .addFilterBefore(new JwtFilter(jwtUtil), LoginFilter.class);

    // 필터 추가 LoginFilter()는 인자를 받음 (AuthenticationManager() 메소드에 authenticationConfiguration 객체를 넣어야 함) 따라서 등록 필요
    // AuthenticationManager()와 JwtUtil 인수 전달
    http
        .addFilterAt(new LoginFilter(
            authenticationManager(authenticationConfiguration), jwtUtil),
            UsernamePasswordAuthenticationFilter.class
        );

    // 세션 설정
    http
        .sessionManagement((session) -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    return http.build();
  }

}
