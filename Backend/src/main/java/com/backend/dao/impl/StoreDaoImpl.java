package com.backend.dao.impl;

import com.backend.dao.StoreDao;
import com.backend.dto.store.Store;
import com.backend.mapper.StoreMapper;
import com.backend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StoreDaoImpl implements StoreDao {
    @Autowired
    StoreMapper mapper;


    @Override
    public Store findById(Long id) {
        return mapper.findById(id);
    }

    @Override
    public List<Store> findByKeyword(String keyword) {
        return mapper.findByKeyword(keyword);
    }

    @Override
    public List<Store> findAllStore() {
        return mapper.findAllStore();
    }
}
