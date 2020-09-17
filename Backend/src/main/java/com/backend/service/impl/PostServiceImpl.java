package com.backend.service.impl;

import com.backend.dao.PostDao;
import com.backend.dto.post.Post;
import com.backend.dto.post.Tag;
import com.backend.dto.user.User;
import com.backend.service.BookmarkService;
import com.backend.service.PostService;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostDao postDao;

    @Autowired
    BookmarkService bookmarkService;

    @Override
    public Long save(Post post) {
        postDao.save(post);
        Long pid = post.getPid();
        if(post.getTag() != null) {
            for (String tag : post.getTag()) {
                Long tid = postDao.findTagByName(tag);
                if (tid != null) { // 태그가 이미 존재하는 경우
                    postDao.increaseTagHits(tag); // 해당 태그의 사용 횟수를 1증가
                } else {
                    Tag newTag = new Tag(tag);
                    postDao.saveNewTag(newTag); // 새로운 태그를 등록한 뒤에
                    tid = newTag.getTid();
                } // 태그가 존재하지 않는 경우
                postDao.savePostTagList(pid, tid); // 태그 리스트에 저장
            }
        }
        return pid;
    }

    @Override
    @Transactional
    public Post findById(Long id) {
        postDao.increaseHits(id);           // 글 조회시 조회수 증가 Transaction 처리
        return postDao.findById(id);
    }

    @Override
    public List<Post> findByTitle(String title) {
        return postDao.findByTitle(title);
    }

    @Override
    public List<Post> findByNickName(String nickname) {
        return postDao.findByNickName(nickname);
    }

    @Override
    public List<Post> findByTag(String tag) {
        return postDao.findByTag(tag);
    }

    @Override
    public List<Post> findAllOrderedByHits() {
        return postDao.findAllOrderedByHits();
    }

    @Override
    public List<Post> findAllOrderedByLikes() {
        return postDao.findAllOrderedByLikes();
    }

    @Override
    public List<Post> findAllOrderedByPublishedTime() {
        return postDao.findAllOrderedByPublishedTime();
    }

    @Override
    public void update(Post post) {
        Long pid = post.getPid();
        if(post.getTag() != null) {
            for (String tag : post.getTag()) {
                Long tid = postDao.findTagByName(tag);
                if (tid != null) { // 태그가 이미 존재하는 경우
                    postDao.increaseTagHits(tag); // 해당 태그의 사용 횟수를 1증가
                } else {
                    Tag newTag = new Tag(tag);
                    postDao.saveNewTag(newTag); // 새로운 태그를 등록한 뒤에
                    tid = newTag.getTid();
                } // 태그가 존재하지 않는 경우
                postDao.savePostTagList(pid, tid); // 태그 리스트에 저장
            }
        }
        postDao.update(post);
    }

    @Override
    public void delete(Long pid) {
        postDao.delete(pid);
    }

    @Override
    public void onClickLikes(Long pid, Long uid, boolean status) {
        if(status) {// 좋아요 활성화
            if(bookmarkService.isBookmark(uid, pid) == null) {
                postDao.increaseLikes(pid);
                bookmarkService.saveBookmark(uid, pid);
            }
            // 사용자의 즐겨찾기 목록에 등록
        } else { // 좋아요 비활성화
            if(bookmarkService.isBookmark(uid, pid) != null) {
            postDao.decreaseLikes(pid);
            // 사용자의 즐겨찾기 목록에서 삭제
            bookmarkService.deleteBookmark(uid, pid);
            }
        }
    }

    @Override
    public List<String> findAllPostTags(Long pid) {
        return postDao.findAllPostTags(pid);
    }

    @Override
    public List<Post> findLatestNotice() {
        return postDao.findLatestNotice();
    }

    @Override
    public void temporarily(Post post, boolean isNewPost) {
        Long pid = post.getPid();
        if(post.getTag() != null && !isNewPost) {
            for (String tag : post.getTag()) {
                Long tid = postDao.findTagByName(tag);
                if (tid != null) { // 태그가 이미 존재하는 경우
                    postDao.increaseTagHits(tag); // 해당 태그의 사용 횟수를 1증가
                } else {
                    Tag newTag = new Tag(tag);
                    postDao.saveNewTag(newTag); // 새로운 태그를 등록한 뒤에
                    tid = newTag.getTid();
                } // 태그가 존재하지 않는 경우
                postDao.savePostTagList(pid, tid); // 태그 리스트에 저장
            }
        }
        postDao.temporarily(post);
    }

//    @Override
//    public String getQRImage(Long uid) {
//        return postDao.getQRImage(uid);
//    }

    @Override
    public List<Post> findAllTempPost(Long uid) {
        return postDao.findAllTempPost(uid);
    }

    @Override
    public List<Post> findAllUserPost(Long uid) {
        return postDao.findAllUserPost(uid);
    }

    @Override
    public User findAuthorInfo(Long uid) {
        return postDao.findAuthorInfo(uid);
    }
}
