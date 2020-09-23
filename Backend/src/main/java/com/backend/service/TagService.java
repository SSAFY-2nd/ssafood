package com.backend.service;

import com.backend.dto.post.Post;
import com.backend.dto.post.Tag;

import java.util.List;

public interface TagService {

    public List<Tag> loadTopTags();

    public List<Post> getAllPostByTag(String name); // 태그를 포함한 게시글을 불러옴

}
