package com.backend.dao;

import com.backend.dto.review.Review;

import java.util.List;

public interface ReviewDao {


    public Long save(Review review); // 리뷰 등록
    public List<Review> findById(Long id); // 음식점 id로 리뷰 조회
    public Review read(Long id); // review_id로 음식점 리뷰 조회
    public void update(Review review); // 리뷰 수정
    public void delete(Long review_id); // 리뷰 삭제

}
