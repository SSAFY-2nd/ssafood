package com.backend.dto.review;


import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    private int review_id; // review 고유 ID
    private int store_id; // store 고유 ID
    private int writer_id; // 작성자 고유ID
    private int gender; // 0-남성 1-여성
    private int born_year; // 태어난 연도
    private int total_score; // 종합 별점 1~5, 0인 경우 미입력
    private String content; // 평가내용
    private String reg_time; // 작성일



}
