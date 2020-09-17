package com.backend.dao.impl;

import com.backend.dao.PostDao;
import com.backend.dto.post.Post;
import com.backend.dto.post.Tag;
import com.backend.dto.user.User;
import com.backend.mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostDaoImpl implements PostDao {

    @Autowired
    PostMapper postMapper;

    @Override
    public Long save(Post post) {
        return postMapper.save(post);
    }

    @Override
    public Post findById(Long id) {
        return postMapper.findById(id);
    }

    @Override
    public List<Post> findByTitle(String title) {
        return postMapper.findByTitle(title);
    }

    @Override
    public List<Post> findByNickName(String nickname) {
        return postMapper.findByNickName(nickname);
    }

    @Override
    public List<Post> findByTag(String tag) {
        return postMapper.findByTag(tag);
    }

    @Override
    public List<Post> findAllOrderedByHits() {
        return postMapper.findAllOrderedByHits();
    }

    @Override
    public List<Post> findAllOrderedByLikes() {
        return postMapper.findAllOrderedByLikes();
    }

    @Override
    public List<Post> findAllOrderedByPublishedTime() {
        return postMapper.findAllOrderedByPublishedTime();
    }

    @Override
    public void update(Post post) {
        postMapper.update(post);
    }

    @Override
    public void delete(Long pid) {
        postMapper.delete(pid);
    }

    @Override
    public void increaseHits(Long pid) {
        postMapper.increaseHits(pid);
    }

    @Override
    public void increaseLikes(Long pid) {
        postMapper.increaseLikes(pid);
    }

    @Override
    public void decreaseLikes(Long pid) {
        postMapper.decreaseLikes(pid);
    }

    @Override
    public List<String> findAllPostTags(Long pid) {
        return postMapper.findAllPostTags(pid);
    }

    @Override
    public Long findTagByName(String tag) {
        return postMapper.findTagByName(tag);
    }

    @Override
    public void saveNewTag(Tag tag) {
        postMapper.saveNewTag(tag);
    }

    @Override
    public void increaseTagHits(String tag) {
        postMapper.increaseTagHits(tag);
    }

    @Override
    public void savePostTagList(Long pid, Long tid) {
        postMapper.savePostTagList(pid, tid);
    }

    @Override
    public List<Post> findLatestNotice() {
        return postMapper.findLatestNotice();
    }

    @Override
    public void temporarily(Post post) {
        postMapper.temporarily(post);
    }

//    @Override
//    public String getQRImage(Long uid) {
//        return postMapper.getQRImage(uid);
//    }

    @Override
    public List<Post> findAllTempPost(Long uid) {
        return postMapper.findAllTempPost(uid);
    }

    @Override
    public List<Post> findAllUserPost(Long uid) {
        return postMapper.findAllUserPost(uid);
    }

    @Override
    public User findAuthorInfo(Long uid) {
        return postMapper.findAuthorInfo(uid);
    }
}
