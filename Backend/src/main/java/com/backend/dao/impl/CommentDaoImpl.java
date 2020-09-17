package com.backend.dao.impl;

import com.backend.dao.CommentDao;
import com.backend.dto.comment.Comment;
import com.backend.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentDaoImpl implements CommentDao {

    @Autowired
    CommentMapper commentMapper;

    @Override
    public Long save(Comment comment) {
        return commentMapper.save(comment);
    }

    @Override
    public Long saveReply(Comment comment) {
        return commentMapper.saveReply(comment);
    }

    @Override
    public void update(Comment comment) {
        commentMapper.update(comment);
    }

    @Override
    public void delete(Long cid) {
        commentMapper.delete(cid);
    }

    @Override
    public List<Comment> findAllChildComment(Long cid) {
        return commentMapper.findAllChildComment(cid);
    }

    @Override
    public List<Comment> findAllComment(Long pid) {
        return commentMapper.findAllComment(pid);
    }
}
