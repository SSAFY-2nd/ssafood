package com.backend.dto.review;


import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    private String reviewList; // 평가 목록

    private String writerInfo; // 작성자 정보
    private int writerId; // 작성자 고유ID
    private int gender; // 0-남성 1-여성
    private String bornYear; // 태어난 연도

    private String reviewInfo; // 평가 정보
    private int reviewId; // 평가 고유 ID
    private int totScore; // 종합 별점 1~5, 0인 경우 미입력
    private int content; // 평가내용
    private String regTime; // 작성일



}
