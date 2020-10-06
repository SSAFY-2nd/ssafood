package com.backend.service.impl;

import com.backend.dao.StoreDao;
import com.backend.dto.store.Store;
import com.backend.dto.store.StoreLength;
import com.backend.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    StoreDao dao;

    @Override
    public Store findById(Long id) {
        return dao.findById(id);
    }

    @Override
    public List<Store> findByKeyword(String keyword) {
        return dao.findByKeyword(keyword);
    }

    @Override
    public List<Store> findAllStore() {
        return dao.findAllStore();
    }

    @Override
    public List<StoreLength> findNearLocation(float latitude, float longitude) {
        return dao.findNearLocation(latitude, longitude);
    }

    @Override
    public List<Store> findPopularLocation(String address) {
        return dao.findPopularLocation(address);
    }

}