package com.backend.dao;

import com.backend.dto.user.User;

public interface UserDao {

    public void save(User c); // 회원 가입
    public void delete(String uid); // 회원 탈퇴
    public User findByUid(String uid); // 회원 정보 확인
    public User findByEmail(String email); // 회원 정보 확인
    public void update(User c); // 회원 정보 수정
}
