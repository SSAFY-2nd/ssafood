package com.backend.dao;

import com.backend.dto.post.Post;
import com.backend.dto.post.Tag;
import com.backend.dto.user.User;

import java.util.List;

public interface PostDao {

    public Long save(Post post); // 글 작성

    public Post findById(Long id); // 포스트 고유 번호(pid) 로 조회

    public List<Post> findByTitle(String title); // 제목으로 조회

    public List<Post> findByNickName(String nickname); // 사용자 이름으로 작성글 조회

    public List<Post> findByTag(String tag); // 태그로 포스트 조회

    public List<Post> findAllOrderedByHits(); // 조회순으로 조회

    public List<Post> findAllOrderedByLikes(); // 좋아요 순으로 조회

    public List<Post> findAllOrderedByPublishedTime(); // 최신글 순으로 조회
    //public List<Post> findAll(); // 모든 글을 조회

    public void update(Post post); // 게시글 수정

    public void delete(Long pid); // 게시글 삭제

    public void increaseHits(Long pid); // 포스트 조회시 조회수 증가

    public void increaseLikes(Long pid); // 게시글 좋아요 등록

    public void decreaseLikes(Long pid); // 게시글 좋아요 해제

    public List<String> findAllPostTags(Long pid); // 포스트에 등록된 태그 정보를 가져온다.

    public Long findTagByName(String tag); // 태그가 존재하는지 태그명으로 조회

    public void saveNewTag(Tag tag); // 새로운 태그를 등록

    public void increaseTagHits(String tag); // 기존에 태그가 존재하는 경우 태그 사용횟수를 1증가

    public void savePostTagList(Long pid, Long tid); // 태그리스트에 pid와 tid 를 매핑하여 저장

    public List<Post> findLatestNotice(); // 가장 최신 공지글을 조회

    public void temporarily(Post post); // 포스트 임시저장

//    public String getQRImage(Long uid); // 작성자의 QRImage 를 가져옴

    public List<Post> findAllTempPost(Long uid); // 사용자의 모든 임시저장 글을 가져옴

    public List<Post> findAllUserPost(Long uid); // 사용자의 모든 게시글을 가져옴

    public User findAuthorInfo(Long uid);   // 작성자의 기본정보를 가져옴

}
