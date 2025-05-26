package com.example.project_board.repository;

import com.example.project_board.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

  // 유저가 존재하는지 확인 -> 있으면 유저 생성하면 X기 때문
  Boolean existsByUsername(String username);

  // username 을 받아 DB 테이블에서 회원을 조회하는 메서드 작성
  UserEntity findByUsername(String username);
}
