package kr.yoonyeong.blog.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kr.yoonyeong.blog.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @author rival
 * @since 2022-02-06
 */
@Slf4j
public class TokenProvider {
  @Value("${token.secret.key}")
  private String SECRET_KEY;

  // 토큰 생성
  public String create(UserEntity userEntity){

    Date expiryDate = Date.from(
      Instant.now().plus(1, ChronoUnit.DAYS)
    );

    return Jwts.builder()
      .setIssuedAt(new Date())
      .setExpiration(expiryDate)
      .setIssuer("demo app")
      .setSubject(userEntity.getId())
      .signWith(SignatureAlgorithm.HS512, SECRET_KEY.getBytes(StandardCharsets.UTF_8))
      .compact();
  }


  // 토큰 검증하고, 토큰에 들어있는 정보 추출(payload 부분)
  public String validateAndGetUserId(String token){
    // claim 추출 - payload 부분
    Claims claims = Jwts.parser()
      .setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
      .parseClaimsJws(token)
      .getBody();

    // 식별자 정보 추출
    return claims.getSubject();
  }

}