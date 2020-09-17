package com.backend.mapper;

import com.backend.dto.comment.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    public Long save(Comment comment); // 댓글 작성

    public Long saveReply(Comment comment); // 대댓글 작성

    public void update(Comment comment); // 댓글 수정

    public void delete(Long cid); // 댓글 삭제

    public List<Comment> findAllChildComment(Long cid); // 모든 대댓글을 조회

    public List<Comment> findAllComment(Long pid); // 모든 댓글들을 조회

}
