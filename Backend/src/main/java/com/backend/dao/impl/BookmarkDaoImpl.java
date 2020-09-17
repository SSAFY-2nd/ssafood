package com.backend.dao.impl;

import com.backend.dao.BookmarkDao;
import com.backend.dto.post.Post;
import com.backend.mapper.BookmarkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookmarkDaoImpl implements BookmarkDao {

    @Autowired
    BookmarkMapper bookmarkMapper;


    @Override
    public void saveBookmark(Long uid, Long pid) {
        bookmarkMapper.saveBookmark(uid, pid);
    }

    @Override
    public void deleteBookmark(Long uid, Long pid) {
        bookmarkMapper.deleteBookmark(uid, pid);
    }

    @Override
    public Long isBookmark(Long uid, Long pid) {
        return bookmarkMapper.isBookmark(uid, pid);
    }

    @Override
    public List<Post> findAllBookmarks(Long uid) {
        return bookmarkMapper.findAllBookmarks(uid);
    }
}
