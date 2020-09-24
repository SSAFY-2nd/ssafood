package com.backend.mapper;

import com.backend.dto.store.Store;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StoreMapper {
    // CRUD 생성

    public Store findById(Long id); // 한개의 음식점 고유 번호로 조회

    public List<Store> findByKeyword(String keyword); // keyword로  검색

}
