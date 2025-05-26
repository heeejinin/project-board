package com.example.project_board.service;

import com.example.project_board.dto.JoinDto;
import com.example.project_board.entity.UserEntity;
import com.example.project_board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  public void joinProcess(JoinDto joinDto) {

    String username = joinDto.getUsername();
    String password = joinDto.getPassword();

    // 유저가 존재하는지 확인 -> 있으면 유저 생성하면 X기 때문
    Boolean isExist = userRepository.existsByUsername(username);
    if (isExist) {
      return ;
    }

    UserEntity data = new UserEntity();
    data.setUsername(username);
    data.setPassword(bCryptPasswordEncoder.encode(password)); // 암호화된 비밀번호
    data.setRole("ROLE_ADMIN");
    userRepository.save(data);
  }

}
