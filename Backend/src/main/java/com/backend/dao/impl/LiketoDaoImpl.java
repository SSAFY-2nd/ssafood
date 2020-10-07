package com.backend.dao.impl;

import com.backend.dao.LiketoDao;
import com.backend.dto.liketo.Liketo;
import com.backend.dto.store.Store;
import com.backend.mapper.LiketoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LiketoDaoImpl implements LiketoDao {

    @Autowired
    LiketoMapper mapper;


    @Override
    public void likeFlag(int uid, int store_id, int isLike) {
        mapper.likeFlag(uid, store_id, isLike);
        return ;
    }

    @Override
    public Liketo findStatus(int uid, int store_id) {
        return mapper.findStatus(uid, store_id);
    }

    @Override
    public List<Store> findLikedList(int uid) {
        return mapper.findLikedList(uid);
    }

    @Override
    public Liketo findisExist(int uid, int store_id) {
        return mapper.findisExist(uid, store_id);
    }

    @Override
    public void insertLike(int uid, int store_id, int isLike) {
        mapper.insertLike(uid, store_id, isLike);
        return ;
    }
}
