package com.example.project_board.jwt;

import io.jsonwebtoken.Jwts;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  private final SecretKey secretKey;

  public JwtUtil(@Value("${spring.jwt.secret}")String secret) {

    this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
  }

  // 검증 메서드 3가지
  public String getUsername(String token) {
    return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
  }

  public String getRole(String token) {
    return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
  }

  // 유효기간 검증
  public Boolean isExpired(String token) {
    return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
  }

  // 로그인 성공 시 토큰 생성 메서드
  public String createJwt(String username, String role, Long expiredMs) {
    return Jwts.builder()
        .claim("username", username)
        .claim("role", role)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + expiredMs))
        .signWith(secretKey)
        .compact();
  }
}
