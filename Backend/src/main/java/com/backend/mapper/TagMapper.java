package com.backend.mapper;

import com.backend.dto.post.Post;
import com.backend.dto.post.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagMapper {

    public List<Tag> loadTopTags(); // 인기 태그를 가져옴

    public List<Post> getAllPostByTag(String name); // 태그를 포함한 게시글을 불러옴
}
