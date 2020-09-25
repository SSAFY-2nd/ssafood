package com.backend.controller;


import com.backend.dto.user.User;
import com.backend.service.JWTDecoding;
import com.backend.service.JwtService;
import com.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
public class UserController {

    @Autowired
    UserService service;
    User userDao;

    @Autowired
    private JwtService jwtService;

    private final String DEFAULT_PROFILE_IMAGE_URL = "https://lh3.googleusercontent.com/proxy/5Ryjk2vvLRidpN292xq0OLRYGOw6egfRpEkTF8-ZYaM7sUONOg1BOGWmVq4TuDGw0qTRsmN8muPb5L7SavG9FmRSzpw6aSCMkJc4yTTSE17FFftv8ds6PSr70U2sHZoxQk_beal1SGHvk6H84P3-KfiKH483nsV5BoWj4RZFXOxbgdw6_YMSOqga4g4QVBBnqDqDnBEhGTx6jFQW1IRhWb4DBFhMLsxXKEkBgMTgvu7yAVFJ-2vG4xyEO30auFgbIenT3zuw_LMxIfeqybakkWKLga8xM59HgWYKLax63vQsJeQdO6JAAiyWhw_eXurt7AtobB10Jq4SNrKps3a6LfHMAgS7nW3juhtG7c9X1Q";

    @PostMapping("/googlelogin")
    public Object googleLogin(@RequestHeader final HttpHeaders header) throws Exception {

        String jwtToken = header.get("authorization").get(0).substring(7);

        // id_token decode해서 메일/picture 변환.
        String email = JWTDecoding.decode(jwtToken);
        String picture = JWTDecoding.getImg(jwtToken);
        String subPassword = JWTDecoding.getHashuid(jwtToken);
        String nickname = JWTDecoding.getNickname(jwtToken);

        // String Check
//        System.out.println(email);
//        System.out.println(picture);
//        System.out.println(subPassword);
//        System.out.println(nickname);

        // 로그인할 때 사용할 인스턴스 초기화
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        // email 가입 여부 확인
        User memberVo = service.findByEmail(email);


        if (memberVo == null) { // google 계정 회원가입
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setProfileImage(picture);
            newUser.setPassword(subPassword);
            newUser.setNickname(nickname);
            newUser.setProfileImage(DEFAULT_PROFILE_IMAGE_URL);
            signup(newUser); // 회원가입

        }else if(memberVo != null){ // google 계정 로그인
            // 이미 로그인된 유저
        }

        try{

            //로그인
            User loginUser = service.signin(email, subPassword);

            // 로그인 성공했다면 토큰을 생성한다.
            String loginToken = jwtService.create(loginUser);

            // 토큰 정보는 request의 헤더로 보내고 나머지는 Map에 담아둔다.
            resultMap.put("jwt-auth-token", loginToken);
            resultMap.put("status", true);
            resultMap.put("request_body", loginUser);
            status = HttpStatus.ACCEPTED;

        } catch(RuntimeException e){
            log.error("정보조회 실패", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);

    }

    @PostMapping("/api/signin")
    public ResponseEntity<Map<String, Object>> signin(@RequestBody User user, HttpServletResponse res){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        System.out.println("USER:" +user);
        try{

            User loginUser = service.signin(user.getEmail(), user.getPassword());


            // 로그인 성공했다면 토큰을 생성한다.
            String token = jwtService.create(loginUser);
            // 토큰 정보는 request의 헤더로 보내고 나머지는 Map에 담아둔다.
            resultMap.put("jwt-auth-token", token);
            resultMap.put("status", true);
            resultMap.put("request_body", loginUser);
            status = HttpStatus.ACCEPTED;
        } catch(RuntimeException e){
            log.error("정보조회 실패", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }


    /**
     *
     * @param loginVo email을 보낸다.
     * @return True : Email을 만들 수 있을 경우
     *          False : Email을 만들 수 없을 경우
     */

    @PostMapping("/api/emailduplicate")
    public boolean isemailAvailable (@RequestBody User loginVo) {    //HTTP요청의 내용을 객체에 매핑하기 위해 @RequestBody 를 설정.
        // @Controller인 경우 @ResponseBody를 적어야한다.
        String in_email = loginVo.getEmail();

        User memberVo = service.findByEmail(in_email);    //UserID로 user가 존재하는지 확인.
        if (memberVo == null) {
            return true;
        }else if(memberVo != null){
            return false;
        }
        return false;
    }


    /**
     * 
     * @param user
     * @return Success -> "회원가입 완료"
     *          Fail -> "Error MSG"
     */
    @PostMapping("/api/v1")
    public ResponseEntity<?> signup(@RequestBody User user)
    {

        try{
            System.out.println(user);
            service.save(user);
            return new ResponseEntity<>("회원가입 완료", HttpStatus.OK);
        }catch(Exception err){
            String errorMessage;
            errorMessage = err + " <== error ";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     *
     * @param uid
     * @return Success -> "USER 정보"
     *         Fail -> "ERROR MSG"
     */
    @GetMapping("/api/v1/{uid}")
    public Optional<User> findbyUid(@PathVariable String uid){
        return Optional.ofNullable(service.findByUid(uid));
    }


    /**
     * @param uid
     * @return Success -> "회원탈퇴 성공"
     *         Fail -> "ERROR MSG"
     */
    @DeleteMapping("/api/v1/{uid}")
    public ResponseEntity<?> delete(@PathVariable String uid) {
        try{
            service.delete(uid);
            return new ResponseEntity<>("회원탈퇴 성공", HttpStatus.OK);
        }catch(Exception err){
            String errorMessage;
            errorMessage = err + "<== error";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     *
     * @param user
     * @return Success -> "회원수정 성공"
     *         Fail -> "ERROR MSG"
     */
    @PutMapping(value="/api/v1")
    public ResponseEntity<?> update(@RequestBody User user) {
        try{
            service.update(user);
            return new ResponseEntity<>("회원정보수정 성공", HttpStatus.OK);
        }catch(Exception err){
            String errorMessage;
            errorMessage = err + "<== error";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }


}
