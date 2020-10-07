package com.backend.mapper;

import com.backend.dto.bhour.Bhour;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface BhourMapper {
    public List<Bhour> findById(Long id); // 음식점 영업시간 확인
}
