package com.backend.service.impl;

import com.backend.dao.ReviewDao;
import com.backend.dto.review.Review;
import com.backend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    ReviewDao dao;

    @Override
    public Long save(Review review) {
        return dao.save(review);
    }

    @Override
    public List<Review> findById(Long id) {
        return dao.findById(id);
    }

    @Override
    public Review read(Long id) {
        return dao.read(id);
    }

    @Override
    public void update(Review review) {
        dao.update(review);
        return;
    }

    @Override
    public void delete(Long review_id) {
        dao.delete(review_id);
        return ;
    }
}