package com.backend.dto.bhour;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;


@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Bhour {

    private int bhour_id; // bhour 고유 id
    private int store_id;
    private int type; // 1-영업시간 2- 쉬는 시간 3-휴무일
    private int week_type; // 1 - 매주, 2 - 첫째주, 3 - 둘째주, 4 - 셋째주, 5 - 넷째주, 6 - 공휴일
    // 월 ~ 금 ( 영업하면 1, 안하면 0 )
    private int mon;
    private int tue;
    private int wed;
    private int thu;
    private int fri;
    private int sat;
    private int sun;

    private String start_time;

    private String end_time;

    private String etc; // 연중무휴
}
