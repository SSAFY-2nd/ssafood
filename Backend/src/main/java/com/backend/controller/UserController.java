package com.backend.controller;

import com.backend.dto.post.Post;
import com.backend.service.BookmarkService;
import com.backend.service.JWTDecoding;
import com.backend.service.JwtService;
import com.backend.service.UserService;
import com.backend.dto.user.User;
import com.backend.util.SHA512;
import io.jsonwebtoken.Jwt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.awt.SystemColor.info;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
public class UserController {

    @Autowired
    UserService service;
    User userDao;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private BookmarkService bookmarkService;

    private final String DEFAULT_PROFILE_IMAGE_URL = "http://i3a507.p.ssafy.io/img/user/default/profile.png";

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

    /***
     * 회원정보수정
     * 회원정보수정 성공 >> return "회원수정 성공"
     * 실패 >> return ERROR MSG
     */

    @PutMapping(value="/api/v1/{uid}")
    public ResponseEntity<?> update(@PathVariable String uid, @RequestBody User user) {
        try{
            switch(user.getUpdateType()){
                // modifyType:2 nickname, introduce
                case "1":
                    service.updateAllPostsNickName(uid, user.getNickname());
                    service.updateIntroduce(user.getEmail(), user.getNickname(), user.getIntroduce());
                    break;
                // modifyType:3 SNS Info
                case "2":service.updateSNS(user.getEmail(), user.getFacebook(), user.getGithub(), user.getInstagram());
                    break;
            }

            return new ResponseEntity<>("회원정보수정 성공", HttpStatus.OK);
        }catch(Exception err){
            String errorMessage;
            errorMessage = err + "<== error";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
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

    // 회원가입
    // 회원가입 정상 동작 >> return "회원가입 완료"
    // 실패 >> return ERROR MSG
    @PostMapping("/api/v1")
    public ResponseEntity<?> signup(@RequestBody User user)
    {
        user.setProfileImage(DEFAULT_PROFILE_IMAGE_URL);
        System.out.println("회원가입: " +user.toString());
        try{
            service.save(user);
            return new ResponseEntity<>("회원가입 완료",HttpStatus.OK);
        }catch(Exception err){
            String errorMessage;
            errorMessage = err + " <== error ";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

    // 회원정보확인
    // 회원정보확인 정상 동작 >> return "User값"
    // 실패 >> return null
    @GetMapping("/api/v1/{uid}")
    public Optional<User> findbyUid(@PathVariable String uid){
        return Optional.ofNullable(service.findByUid(uid));
    }

    // 회원탈퇴
    // 회원탈퇴 정상 동작 >> return "회원탈퇴 성공"
    // 실패 >> return ERROR MSG
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


    // 사용자의 모든 북마크 읽어오기

    /**
     * 사용자의 모든 북마크 읽어오기
     * @param uid
     * @return 메인의 카드뉴스 형식처럼 북마크로 등록된 모든 포스트를 읽어온다.
     */
    @ApiOperation(value = "사용자의 모든 북마크 가져오기", notes = "사용자의 모든 북마크를 읽어온다.")
    @GetMapping("/api/v1/likes/{uid}")
    public List<Post> findAllBookmarks(@PathVariable Long uid) {
        return bookmarkService.findAllBookmarks(uid);
    }


    /**
     * 사용자 프로필 이미지 업로드
     * @param file
     * @param uid
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "사용자 프로필 이미지 업로드", notes = "사용자 프로필 이미지 업로드, 파일, 사용자 닉네임")
    @PostMapping(value = "/api/v1/img")
    public String uploadProfileImage(@RequestParam("file") MultipartFile file,
                                        @RequestParam("uid") String uid) throws Exception {

        // 이미지 파일 파싱
        String fullFileName = file.getOriginalFilename(); // 파일명 + 확장자
        String originFileName = fullFileName.substring(0, fullFileName.indexOf('.')); // 순수 파일명 확장자 제거
        String extension = fullFileName.substring(fullFileName.indexOf('.')); // 파일 확장자

        // 이미지 파일 이름 암호화
        SHA512 filename = new SHA512(originFileName); // 파일명 SHA-512 암호화

        // 프로필 저장할 경로 생성
        // 루트경로 + user/ + uid/ + profile
        String basePath = "/home/ubuntu/dist/dist/img/user/" + uid + "/profile";

        // 경로에 디렉토리가 존재하지 않을 경우 폴더 생성
        File dir = new File(basePath);
        if(!dir.exists()) {
            dir.mkdirs();
        }

        // 파일 저장
        String filePath = basePath + "/" + filename.getSha512() + extension;
        File location = new File(filePath);
        file.transferTo(location);

        // 저장된 파일이 위치한 경로
        String url = filePath.replace("/home/ubuntu/dist/dist/", "http://i3a507.p.ssafy.io/");

        // profileImage 필드에 저장하기
        service.updateProfileImage(uid, url);

        return url;
    }

    /**
     * 후원을 위한 QR 이미지 업로드
     * @param file
     * @param uid
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "후원을 위한 QR 이미지 업로드", notes = "QR 이미지 업로드, 파일, 사용자 닉네임")
    @PostMapping(value = "/api/v1/qr")
    public String uploadQRImage(@RequestParam("file") MultipartFile file,
                                        @RequestParam("uid") String uid) throws Exception {
        // 이미지 파일 파싱
        String fullFileName = file.getOriginalFilename(); // 파일명 + 확장자
        String originFileName = fullFileName.substring(0, fullFileName.indexOf('.')); // 순수 파일명 확장자 제거
        String extension = fullFileName.substring(fullFileName.indexOf('.')); // 파일 확장자

        // 이미지 파일 이름 암호화
        SHA512 filename = new SHA512(originFileName); // 파일명 SHA-512 암호화

        // 프로필 저장할 경로 생성
        // 루트경로 + user/ + uid/ + profile
        String basePath = "/home/ubuntu/dist/dist/img/user/" + uid + "/qr";

        File dir = new File(basePath); // 경로에 디렉토리가 존재하지 않을 경우 폴더 생성
        if(!dir.exists()) {
            dir.mkdirs();
        }

        // 파일 저장
        String filePath = basePath + "/" + filename.getSha512() + extension;
        File location = new File(filePath);
        file.transferTo(location);

        // 저장된 파일이 위치한 경로
        String url = filePath.replace("/home/ubuntu/dist/dist/", "http://i3a507.p.ssafy.io/");

        service.updateQRImage(uid, url);

        return url;
    }

    /**
     *
     * @param uid
     * @return void
     */
    @PutMapping("/api/v1/img/{uid}")
    public void  deleteProfile(@PathVariable String uid, @RequestBody Map<String, String> param) {
        String path = "/home/ubuntu/dist/dist/img/user/" + uid + "/profile";
        File dir  = new File(path);

        // 디렉토리 내의 모든 파일 삭제
        File[] list = dir.listFiles();
        for(int i = 0; i<list.length; i++) {
            list[i].delete();
        }

        service.updateDefaultProfile(uid, DEFAULT_PROFILE_IMAGE_URL);
    }

    /**
     *
     * @param uid
     * @return void
     */

    @PutMapping("/api/v1/qr/{uid}")
    public void  deleteQR(@PathVariable String uid, @RequestBody Map<String, String> param) {
        User user = service.findByUid(uid);
        String path = "/home/ubuntu/dist/dist/img/user/" + uid + "/qr";
        File dir  = new File(path);

        // 디렉토리 내의 모든 파일 삭제
        File[] list = dir.listFiles();
        for(int i = 0; i<list.length; i++) {
            list[i].delete();
        }

        service.updateDefaultQR(uid, null);
    }


}