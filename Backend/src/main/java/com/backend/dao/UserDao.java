package com.backend.dao;

import com.backend.dto.user.User;

public interface UserDao {

    public void save(User c); // 회원 가입

    public void delete(String uid); // 회원 탈퇴

    public User findByUid(String uid); // 회원 정보 확인

    public User findByEmail(String email); // 회원 정보 확인

    public void updateIntroduce(String email, String nickname, String introduce); // 자기소개 업데이트

    public void updateSNS(String email, String facebook, String github, String instagram); // SNS 계정 업데이트

    public void updateProfileImage(String uid, String url); // 프로필 이미지 업데이트

    public void updateQRImage(String uid, String url); // QR 이미지 업데이트

    public void updateDefaultProfile(String uid, String url); // Profile 삭제

    public void updateDefaultQR(String uid, String url); // QR 삭제

    public User signin(String email, String password);

    public void updateAllPostsNickName(String uid, String nickname); // 유저 닉네임 변경시 모든 글 닉네임 변경

}
