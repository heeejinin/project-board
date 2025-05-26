package com.example.project_board.service;

import com.example.project_board.dto.CustomUserDetails;
import com.example.project_board.entity.UserEntity;
import com.example.project_board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  // DB 연결을 위한 객체 변수 선언
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    // DB에서 조회
    UserEntity userData = userRepository.findByUsername(username);

    if (userData != null) {

      return new CustomUserDetails(userData);

    }
    throw new UsernameNotFoundException("User not found with username: " + username);
  }
}
