package com.backend.dto.post;

import com.backend.dto.comment.Comment;
import com.backend.dto.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    private Long pid;               // 작성글에 고유하게 부여되는 아이디 (PK)
    private Long uid;               // 글 작성자의 고유 아이디 (FK)

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedTime; // 글 작성 시간 위의 패턴에 적용된 형식으로 받아옴

    private String author;          // 글 작성자
    private String title;           // 글 제목
    private String content;         // 글 내용
    private Long hits;              // 글 조회수
    private Long likes;             // 글 좋아요 개수
    private String thumbnail;       // 섬네일 이미지 경로

    private Boolean isTemp;         // 임시저장 글인지 여부를 판단하는 상태값
    private String category;        // 글 카테고리
    private List<String> tag;       // 글에 적용된 태그
    private List<Comment> comments; // 글에 작성된 모든 댓글

    private Boolean isLike;         // 좋아요 체크 여부
    private User authorInfo;            // 작성자
}
