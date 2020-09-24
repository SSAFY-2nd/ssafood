package com.backend.dto.store;

import com.backend.dto.review.Review;
import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoreDetail {

    // store
    private int id; // 음식점 고유 식별 번호
    private String name; // 식당명
    private String branch; // 지점명
    private String area; // 지역명
    private String tel; // 전화번호
    private String address; // 주소
    private float latitude; // 위도
    private float longtitude; // 경도

    private List<String> category; // 카테고리 목록
    private List<String> menu; // 메뉴 목록

    // bhour
    private String workingDay; // 근무요일

    // review
    private List<Review> reviewList;

}
