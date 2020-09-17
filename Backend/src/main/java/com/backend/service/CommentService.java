package com.backend.service;

import com.backend.dto.comment.Comment;

import java.util.List;

public interface CommentService {

    public Long save(Comment comment); // 댓글 작성

    public Long saveReply(Comment comment); // 대댓글 작성

    public void update(Comment comment); // 댓글 수정

    public void delete(Long cid); // 댓글 삭제

    public List<Comment> findAllCommentsInPost(Long pid);

}
