package com.backend.service.impl;

import com.backend.dao.CommentDao;
import com.backend.dto.comment.Comment;
import com.backend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentDao commentDao;

    @Override
    public Long save(Comment comment) {
        return commentDao.save(comment);
    }

    @Override
    public Long saveReply(Comment comment) {
        return commentDao.saveReply(comment);
    }

    @Override
    public void update(Comment comment) {
        commentDao.update(comment);
    }

    @Override
    public void delete(Long cid) {
        commentDao.delete(cid);
    }

    @Override
    public List<Comment> findAllCommentsInPost(Long pid) { // 글에 해당하는 모든 댓글을 조회
        List<Comment> commentList = commentDao.findAllComment(pid);
        for(Comment comment : commentList) { // 해당 댓글들의 대댓글을 조회
            comment.setChild(commentDao.findAllChildComment(comment.getCid())); // 해당 댓글의 대댓글을 조회
            System.out.println(comment.getChild().toString());
        }
        return commentList;
    }

}
