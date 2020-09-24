package com.backend.dto.store;


import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Store {

    private int id; // 음식점 고유 식별 번호
    private String name; // 식당명
    private String branch; // 지점명
    private String area; // 지역명
    private String tel; // 전화번호
    private String address; // 주소
    private float latitude; // 위도
    private float longitude; // 경도

    private String categoryList; // 카테고리 목록
    private List<String> category; // 카테고리

    private String menuList; // 메뉴 목록
    private List<String> menu; // 메뉴명
    private List<String> price; // 가격

    private List<String> bhourList; // 영업시간 목록
    private int type; // 1-영업시간 2- 쉬는 시간 3-휴무일
    private int week_type; // 1 - 매주, 2 - 첫째주, 3 - 둘째주, 4 - 셋째주, 5 - 넷째주, 6 - 공휴일
    private int[] workingDay; // 월 ~ 일 0, 1
    private String startTime;
    private String endTime;
    private String etc; // 연중무휴

}
