package com.backend.dto.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    private Long pid; // 포스트 아이디
    private Long uid; // 유저 아이디
    private Long cid; // 코멘트 아이디
    private Long parent; // 대댓글의 경우 부모 댓글의 아이디, 부모 댓글은 parent 값이 0

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registrationTime; // 댓글 작성시간

    private String author; // 댓글 작성자
    private String content; // 댓글 내용
    private List<Comment> child; // 댓글에 달린 대댓글 목록

}
