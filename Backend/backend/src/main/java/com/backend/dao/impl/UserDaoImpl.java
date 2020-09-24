package com.backend.dao.impl;

import com.backend.dao.UserDao;
import com.backend.dto.user.User;
import com.backend.mapper.UserMapper;
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
    public void update(User c) {
        mapper.update(c);
    }

}
