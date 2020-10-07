package com.backend.controller;


import com.backend.dto.review.Review;
import com.backend.dto.store.Store;
import com.backend.service.BhourService;
import com.backend.service.ReviewService;
import com.backend.service.StoreService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
public class ReviewController {

    @Autowired
    StoreService storeService;

    @Autowired
    BhourService bhourService;

    @Autowired
    ReviewService reviewService;

    /**
     * @param review
     * @return null;
     */
    @ApiOperation(value = "review", notes = "Review 작성")
    @PostMapping("/api/v1/review")
    public void saveReview(@RequestBody Review review) {
        try{

            reviewService.save(review);
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
        System.out.println("Review 작성 성공");
    }

    /**
     * @param id
     * @return Review 리턴;
     */
    @ApiOperation(value = "review", notes = "Review 읽기")
    @GetMapping("/api/v1/review/{id}")
    public Review readReview(@PathVariable String id) {
        Review review = null;
        Long input_id = Long.parseLong(id);
        try{
            review = reviewService.read(input_id);
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
        System.out.println("Review 조회 성공");
        return review;
    }

    /**
     * @param review
     * @return null;
     */
    @ApiOperation(value = "review", notes = "Review 수정")
    @PutMapping("/api/v1/review")
    public void updateReview(@RequestBody Review review) {
        try{
            reviewService.update(review);
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
        System.out.println("Review 수정 성공");
    }

    /**
     * @param review_id
     * @return null;
     */
    @ApiOperation(value = "review_id", notes = "Review 삭제")
    @DeleteMapping("/api/v1/review/{review_id}")
    public void deleteReview(@PathVariable String review_id) {
        Long input_id = Long.parseLong(review_id);
        try{
            System.out.println(input_id);
            reviewService.delete(input_id);
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
        System.out.println("Review 삭제 성공");
    }


}
