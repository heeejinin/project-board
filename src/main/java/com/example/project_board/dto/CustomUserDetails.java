package com.example.project_board.dto;

import com.example.project_board.entity.UserEntity;
import java.util.ArrayList;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

  private final UserEntity userEntity;

  // Role 값 반환
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Collection<GrantedAuthority> collection = new ArrayList<>();

    collection.add(new GrantedAuthority() {

      @Override
      public String getAuthority() {

        return userEntity.getRole();

      }
    });

    return collection;
  }


  @Override
  public String getPassword() {
    return userEntity.getPassword();
  }

  @Override
  public String getUsername() {
    return userEntity.getUsername();
  }

  // 계정 만료 여부 (기본 true)
  @Override
  public boolean isAccountNonExpired() {
    return UserDetails.super.isAccountNonExpired();
  }

  // 계정 잠김 여부 (기본 true)
  @Override
  public boolean isAccountNonLocked() {
    return UserDetails.super.isAccountNonLocked();
  }

  // 비밀번호 만료 여부 (기본 true)
  @Override
  public boolean isCredentialsNonExpired() {
    return UserDetails.super.isCredentialsNonExpired();
  }

  // 계정 활성화 여부 (기본 true)
  @Override
  public boolean isEnabled() {
    return UserDetails.super.isEnabled();
  }
}
