package com.backend.dao.impl;


import com.backend.dao.ReviewDao;
import com.backend.dto.review.Review;
import com.backend.mapper.ReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewDaoImpl implements ReviewDao {

    @Autowired
    ReviewMapper mapper;

    @Override
    public Long save(Review review) {
        return mapper.save(review);
    }

    @Override
    public List<Review> findById(Long id) {
        return mapper.findById(id);
    }

    @Override
    public Review read(Long id) {
        return mapper.read(id);
    }

    @Override
    public void update(Review review) {
        mapper.update(review);
        return ;
    }

    @Override
    public void delete(Long review_id) {
        mapper.delete(review_id);
        return ;
    }
}
