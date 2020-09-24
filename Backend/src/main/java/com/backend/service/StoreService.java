package com.backend.service;

import com.backend.dto.store.Store;

import java.util.List;

public interface StoreService {

    // CRUD 생성

    public Store findById(Long id); // 한개의 음식점 고유 번호로 조회

    public List<Store> findByIds(int[] id); // 여러 개의 음식점 고유 번호로 조회

//    public List<Store> Search(String name); // 지역, 식당, 음식으로 조회




}
