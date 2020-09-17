package com.backend.dto.user;

import lombok.*;
@Data
@ToString
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String uid;            // (PK) 회원이 가지고 있는 고유키
    private String nickname;       // 회원 닉네임
    private String email;          // (UN) 이메일
    private String password;       // 비밀번호
    private String profileImage;   // 프로필 이미지
    private String qrImage;        // QR코드 이미지
    private Boolean admin;         // 관리자이면 True, 아니면 False
    private String facebook;       // facebook 주소
    private String instagram;      // instagram 주소
    private String github;         // github 주소
    private String introduce;      // 프로필 소개
    private String updateType;     // 업데이트 타입

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String qrImage, String profileImage, String introduce,
                String facebook, String instagram, String github) {
        this.introduce = introduce;
        this.qrImage = qrImage;
        this.profileImage = profileImage;
        this.facebook = facebook;
        this.instagram = instagram;
        this.github = github;
    }

}