package com.backend.dao;

import com.backend.dto.post.Post;

import java.util.List;

public interface BookmarkDao {

    public void saveBookmark(Long uid, Long pid); // 북마크 등록

    public void deleteBookmark(Long uid, Long pid); // 북마크 삭제

    public Long isBookmark(Long uid, Long pid); // 북마크로 등록되어있는지 확인

    public List<Post> findAllBookmarks(Long uid); // 사용자의 전체 북마크 목록을 가져옴

}
