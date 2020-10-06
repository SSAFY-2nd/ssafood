package com.backend.service;

import com.backend.dto.bhour.Bhour;
import com.backend.dto.user.User;

import java.util.List;

public interface BhourService {

    public List<Bhour> findById(Long id); // 음식점 영업시간 확인
}
