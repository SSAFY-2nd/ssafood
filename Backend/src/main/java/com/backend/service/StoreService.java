package com.backend.service;

import com.backend.dto.store.Store;

import java.util.List;

public interface StoreService {

    // CRUD 생성

    public Store findById(Long id); // 한개의 음식점 고유 번호로 조회

    public List<Store> findByKeyword(String keyword); // keyword로 지역, 음식점이름, 음식명 검색

    public List<Store> findAllStore(); // 100개 음식점 검색

    public List<Store> findNearLocation(String address); // address : 도로명 주소

    public List<Store> findPopularLocation(String address); // address : 도로명 주소, 주변 인기 맛집 추천

}
