package com.backend.dao.impl;

import com.backend.dao.UserDao;
import com.backend.mapper.UserMapper;
import com.backend.dto.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    UserMapper mapper;

    @Override
    public void save(User c) {

        mapper.save(c);
    }

    @Override
    public void delete(String uid) {
        mapper.delete(uid);
    }

    @Override
    public User findByUid(String uid) {
        return mapper.findByUid(uid);
    }

    @Override
    public User findByEmail(String email) {
        return mapper.findByEmail(email);
    }


    @Override
    public void updateIntroduce(String email, String nickname, String introduce) {
        mapper.updateIntroduce(email, nickname, introduce);
    }

    @Override
    public void updateSNS(String email, String facebook, String github, String instagram) {
        mapper.updateSNS(email, facebook, github, instagram);
    }

    @Override
    public void updateProfileImage(String uid, String url) {
        mapper.updateProfileImage(uid, url);
    }

    @Override
    public void updateQRImage(String uid, String url) {
        mapper.updateQRImage(uid, url);
    }

    @Override
    public void updateDefaultProfile(String uid, String url) {
        mapper.updateDefaultProfile(uid, url);
    }


    @Override
    public void updateDefaultQR(String uid, String url) {
        mapper.updateDefaultQR(uid, url);
    }


    @Override
    public User signin(String email, String password) {
        return mapper.signin(email, password);
    }

    @Override
    public void updateAllPostsNickName(String uid, String nickname) {
        mapper.updateAllPostsNickName(uid, nickname);
    }
}
