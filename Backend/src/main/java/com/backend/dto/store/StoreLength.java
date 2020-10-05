package com.backend.dto.store;


import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoreLength {
    private int store_id; // 음식점 고유 식별 번호
    private String name; // 식당명
    private String branch; // 지점명
    private String area; // 지역명
    private String tel; // 전화번호
    private String address; // 주소
    private float lat; // 위도
    private float lng; // 경도

    private String category; // 카테고리 목록
    private String menu; // 메뉴 목록
    private String distance; // 거리
}
