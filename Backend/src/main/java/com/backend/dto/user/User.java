package com.backend.dto.user;


import lombok.*;

@ToString
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String uid;            // (PK) 회원이 가지고 있는 고유키
    private String nickname;       // 회원 닉네임
    private String email;          // (UN) 이메일
    private String password;       // 비밀번호
    private String age; // 나이
    private String sex; // 성별 남자 : 0, 여자 : 1
    private String profileImage;   // 프로필 이미지

    public User(String nickname, String email, String password, String age, String sex) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.age = age;
        this.sex = sex;
    }
}
