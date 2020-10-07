package com.backend.service.impl;

import com.backend.dao.LiketoDao;
import com.backend.dto.liketo.Liketo;
import com.backend.dto.store.Store;
import com.backend.service.LiketoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LiketoServiceImpl implements LiketoService {

    @Autowired
    LiketoDao dao;


    @Override
    public void likeFlag(int uid, int store_id, int isLike) {
        dao.likeFlag(uid, store_id, isLike);
        return;
    }

    @Override
    public Liketo findStatus(int uid, int store_id) {
        return dao.findStatus(uid, store_id);
    }

    @Override
    public List<Store> findLikedList(int uid) {
        return dao.findLikedList(uid);
    }

    @Override
    public Liketo findisExist(int uid, int store_id) {
        return dao.findisExist(uid, store_id);
    }

    @Override
    public void insertLike(int uid, int store_id, int isLike) {
        dao.insertLike(uid, store_id, isLike);
        return ;
    }
}
