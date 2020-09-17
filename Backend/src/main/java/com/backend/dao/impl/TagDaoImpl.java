package com.backend.dao.impl;

import com.backend.dao.TagDao;
import com.backend.dto.post.Post;
import com.backend.dto.post.Tag;
import com.backend.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagDaoImpl implements TagDao {

    @Autowired
    TagMapper tagMapper;

    @Override
    public List<Tag> loadTopTags() {
        return tagMapper.loadTopTags();
    }

    @Override
    public List<Post> getAllPostByTag(String name) {
        return tagMapper.getAllPostByTag(name);
    }
}
