package com.backend.service;


import com.backend.dto.user.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;


public interface JwtService  {


    /**
     * 로그인 성공 시 사용자 정보를 기반으로 JWTTOKEN을 생성해서 반환한다.
     * @param user
     * @return
     */
    public String create(final User user);

    /**
     * 전달받은 토큰이 제대로 생성된 것인지 확인하고 문제가 있다면 Runtime 예외를 발생시킨다.
     * @param jwt
     */
    public void checkValid(final String jwt);

    /**
     * jwt 토큰을 분석해서 필요한 정보를 반환한다.
     * @param jwt
     * @return
     */
    public Map<String, Object> get(final String jwt);




}
