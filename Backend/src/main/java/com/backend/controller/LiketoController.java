package com.backend.controller;

import com.backend.dto.liketo.Liketo;
import com.backend.dto.review.Review;
import com.backend.dto.store.Store;
import com.backend.service.BhourService;
import com.backend.service.LiketoService;
import com.backend.service.ReviewService;
import com.backend.service.StoreService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
public class LiketoController {

    @Autowired
    StoreService storeService;

    @Autowired
    BhourService bhourService;

    @Autowired
    ReviewService reviewService;

    @Autowired
    LiketoService liketoService;

    // 특정 음식점만 좋아요
    /**
     * @param liketo -> uid , store_id, isLike( 0 or 1 )
     * @return Liketo Entity;
     */

    @ApiOperation(value = "uid(로그인된 사용자id), store_id(음식점 store_id), isLike(0 or 1)", notes = "음식점 좋아요")
    @PostMapping("/api/v1/like")
    public Liketo likeStore(@RequestBody Liketo liketo) {

        Liketo isExist = liketoService.findStatus(liketo.getUid(), liketo.getStore_id());
        Liketo resultLike = liketo;

        System.out.println("resultLike: " +resultLike);
        System.out.println("isExist: " +isExist);

        if(isExist == null){
            // insert
            liketoService.insertLike(liketo.getUid(), liketo.getStore_id(), liketo.getIsLike());
        }else{
            // modify
            liketoService.likeFlag(liketo.getUid(), liketo.getStore_id(), liketo.getIsLike());
            resultLike = liketoService.findStatus(liketo.getUid(), liketo.getStore_id());

        }

        return resultLike;
    }

    /**
     * @param uid
     * @return 로그인된 아이디의 좋아요한 음식점 리스트
     */
    @ApiOperation(value = "uid(로그인된 사용자id)", notes = "음식점 좋아요")
    @GetMapping("/api/v1/like/{uid}")
    public List<Store> likedList(@PathVariable int uid) {

        //사용자 uid를 통한 검색
        List<Store> likedList = liketoService.findLikedList(uid);

        return likedList;
    }

}
