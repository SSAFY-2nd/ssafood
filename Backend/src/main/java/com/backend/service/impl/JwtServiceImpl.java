package com.backend.service.impl;

import com.backend.dto.user.User;
import com.backend.service.JwtService;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
@Slf4j
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.salt}")
    private String salt;

    @Value("${jwt.expmin}")
    private Long expireMin;

    @Override
    public String create(User user) {
        log.trace("time: {}", expireMin);
        final JwtBuilder builder = Jwts.builder();
        // JWT Token = Header + Payload + Signature
        // Header 설정
        builder.setHeaderParam("typ", "JWT");

        // Payload 설정 - claim 정보 포함
        builder.setSubject("로그인토큰") // 토큰 제목 설정
                .setExpiration(new Date(System.currentTimeMillis()+ 1000*60*expireMin)) // 유효 기간
                .claim("User",user).claim("second", "더 담고 싶은 것"); // 담고 싶은 정보 설정

        // Signature - secret key를 이용한 암호화
        builder.signWith(SignatureAlgorithm.HS256, salt.getBytes());

        // 마지막 직렬화 처리
        final String jwt = builder.compact();
        log.debug("토큰 발행: {}",jwt);
        return jwt;
    }

    @Override
    public void checkValid(String jwt) {
        // 예외 처리가 발생하지 않으면 OK
        log.trace("토큰 점검: {}", jwt);
        Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt);
    }

    @Override
    public Map<String, Object> get(String jwt) {
        Jws<Claims> claims = null;
        System.out.println("JWT:" + jwt);
        try{
            claims = Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt);
        }catch(final Exception e){
            throw new RuntimeException();
        }

        log.trace("claims: {}", claims);
        // Claims는 Map의 구현체이다.
        return claims.getBody();
    }
}
