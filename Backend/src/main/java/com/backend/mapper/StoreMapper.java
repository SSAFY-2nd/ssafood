package com.backend.mapper;

import com.backend.dto.store.Store;
import com.backend.dto.store.StoreLength;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StoreMapper {
    // CRUD 생성

    public Store findById(Long id); // 한개의 음식점 고유 번호로 조회

    public List<Store> findByKeyword(String keyword); // keyword로  검색

    public List<Store> findAllStore(); // 100개 음식점 검색

    public List<StoreLength> findNearLocation(float latitude, float longtitude); // address : 도로명 주소

    public List<Store> findPopularLocation(String address); // address : 도로명 주소, 주변 인기 맛집 추천

}
