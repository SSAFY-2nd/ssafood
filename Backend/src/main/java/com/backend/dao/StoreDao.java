package com.backend.dao;

import com.backend.dto.store.Store;

import java.util.List;

public interface StoreDao {

    // CRUD 생성

    public Store findById(Long id); // 한개의 음식점 고유 번호로 조회

    public List<Store> findByIds(int[] id); // 여러 개의 음식점 고유 번호로 조회


}
