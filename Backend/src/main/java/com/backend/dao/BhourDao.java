package com.backend.dao;

import com.backend.dto.bhour.Bhour;

import java.util.List;

public interface BhourDao {
    public List<Bhour> findById(Long id); // 음식점 영업시간 확인
}
