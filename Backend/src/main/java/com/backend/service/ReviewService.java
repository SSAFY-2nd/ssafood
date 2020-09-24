package com.backend.service;

import com.backend.dto.review.Review;
import com.backend.dto.store.Store;

import java.util.List;

public interface ReviewService {

    // CRUD 생성

    public Long save(Review review); // 리뷰 등록
    public List<Review> findById(Long id); // 음식점 id로 리뷰 조회
    public void update(Review review); // 리뷰 수정
    public void delete(Long pid); // 리뷰 삭제
}
