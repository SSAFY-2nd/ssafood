package com.backend.controller;

import com.backend.dto.post.Post;
import com.backend.dto.user.User;
import com.backend.service.BookmarkService;
import com.backend.service.CommentService;
import com.backend.service.PostService;
import com.backend.service.UserService;
import com.backend.util.SHA512;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Api(tags = {"Post"})
@RestController
@CrossOrigin(origins = {"*"})
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    @Autowired
    BookmarkService bookmarkService;

    @Autowired
    UserService userService;

    private final String DEFAULT_THUMBNAIL_IMAGE_URL = "http://i3a507.p.ssafy.io/img/user/default/thumbnail.jpg";

    /**
     * @param newPost : 사용자가 작성한 Post
     * @return 글이 정상적으로 등록된 경우 HttpStatus 201 반환
     */
    @ApiOperation(value = "글 작성", notes = "새로운 포스트를 작성한다.")
    @PostMapping("/api/v2/")
    public HttpStatus save(@RequestBody Post newPost) throws Exception {
        // 섬네일을 등록하지 않으면 기본 섬네일을 등록한다.
        if (newPost.getThumbnail() == null) {
            newPost.setThumbnail(DEFAULT_THUMBNAIL_IMAGE_URL);
        }
        try {
            postService.save(newPost);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpStatus.CREATED;
    }

    /**
     * @param map (uid, pid) : 현재 로그인한 유저 정보와, 조회하려는 글 번호
     * @return
     */
    @ApiOperation(value = "글 읽기", notes = "pid 를 통해 글 하나를 찾아서 반환")
    @PostMapping("/api/v2/p/")
    public Post findById(@RequestBody Map<String, String> map) {
        Long uid;
        try {
            uid = Long.parseLong(map.get("uid")); // 현재 로그인한 사용자 정보
        } catch (Exception e) {
            uid = null; // 로그인 안할경우 null 값 처리
        }

        Long pid = Long.parseLong(map.get("pid")); // 현재 조회한 글 번호
        Post post = postService.findById(pid);
        post.setAuthorInfo(postService.findAuthorInfo(post.getUid()));
        post.setTag(postService.findAllPostTags(pid)); // 게시글의 모든 태그를 불러옴
        post.setComments(commentService.findAllCommentsInPost(pid)); // 게시글의 모든 댓글을 불러옴
        // 게시글 좋아요 표시
        if(uid != null)
            post.setIsLike(bookmarkService.isBookmark(uid, pid) != null ? true : false);
        else
            post.setIsLike(false);
        return post;
    }

    /**
     * @param option : 키워드 없이 옵션별로 전체 글을 조회 (작성일 순, 조회순, 좋아요 순)
     * @return 글이 없으면 null 을 반환
     */
    @ApiOperation(value = "옵션에 따른 전체 글 조회", notes = "조회수, 좋아요 수, 최신순")
    @GetMapping("/api/v2/{option}")
    public List<Post> findAllOrderedByOption(@PathVariable String option) {
        List<Post> list = new ArrayList<>();

        if (option.equals("hits")) { // 조회수가 높은순으로 조회
            list = postService.findAllOrderedByHits();
        } else if (option.equals("likes")) { // 좋아요가 높은순으로 조회
            list = postService.findAllOrderedByLikes();
        } else if (option.equals("latest")) { // 최신글 순으로 조회
            list = postService.findAllOrderedByPublishedTime();
        }

        return list;
    }

    /**
     * @param keyword : 제목으로 키워드를 포함한 글들을 검색
     * @return 해당 키워드를 포함하는 글이 없으면 null 을 반환
     */
    @ApiOperation(value = "검색 : 키워드", notes = "제목에 키워드를 포함한 포스트를 조회")
    @GetMapping("/api/v2/find/t/{keyword}")
    public List<Post> findAllPostsByKeywords(@PathVariable String keyword) {
        List<Post> list = postService.findByTitle(keyword);
        return list;
    }

    /**
     * @param nickname : 사용자 닉네임으로 작성글 조회
     * @return 해당 사용자가 작성글이 없을 경우 null 반환
     */
    @ApiOperation(value = "검색 : 유저 닉네임", notes = "해당 닉네임의 사용자가 작성한 모든 글을 조회")
    @GetMapping("/api/v2/find/u/{nickname}")
    public List<Post> findAllPostsByUserName(@PathVariable String nickname) {
        List<Post> list = postService.findByNickName(nickname);
        return list;
    }

    /**
     * @param tag : 태그로 포스트 조회
     * @return 관련 포스트가 존재하지 않을 경우 null 반환
     */
    @ApiOperation(value = "검색 : 태그", notes = "해당 태그를 포함하는 모든 포스트를 조회")
    @GetMapping("/api/v2/find/a/{tag}")
    public List<Post> findAllPostByTag(@PathVariable String tag) {
        List<Post> list = postService.findByTag(tag);
        return list;
    }

    /**
     * pid, uid, status 값을 JSON 형태로 전달받음
     * status = True : 좋아요 활성화 및 북마크 등록
     * status = False : 좋아요 비활성화 및 북마크 삭제
     */
    @ApiOperation(value = "좋아요 이벤트 처리", notes = "좋아요를 누른 게시물과 토글 상태값을 넘겨받는다")
    @PutMapping("/api/v2/likes")
    public void updateLikes(@RequestBody Map<String, String> likes) {
        Long pid = Long.parseLong(likes.get("pid"));
        Long uid = Long.parseLong(likes.get("uid"));
        Boolean status = Boolean.parseBoolean(likes.get("status"));

        postService.onClickLikes(pid, uid, status);
    }

    /**
     * 공지사항 불러오는 함수, 추후 여러개 공지사항을 불러오는 코드로 바뀔 수 있음
     */
    @ApiOperation(value = "가장 최신 공지사항 5개 조회", notes = "가장 최신의 공지사항을 불러옴, admin = 1")
    @GetMapping("/api/v2/notice")
    public List<Post> loadNotice() {
        return postService.findLatestNotice();
    }

    /**
     * 임시저장 기능 : 넘겨받은 글의 내용을 수정하고 isTemp 속성을 1로 만든다.
     *
     * @param post 나중에 임시저장이 아닌 저장이 된 글들에 대한 처리가 필요함
     */
    @ApiOperation(value = "임시저장", notes = "임시저장을 눌렀을 포스트 처리")
    @PutMapping("/api/v2/temp")
    public void temporarily(@RequestBody Post post) {
        Long pid;
        Boolean isNewPost = false;
        if (post.getPid() == null) {
            isNewPost = true;
            System.out.println("처음 작성하는 게시글 ");
            try {
                pid = postService.save(post);
                post.setPid(pid);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(post.getThumbnail() == null) post.setThumbnail(DEFAULT_THUMBNAIL_IMAGE_URL);
        postService.temporarily(post, isNewPost);
    }


    /**
     * 게시글 삭제 기능 : pid 에 해당하는 게시글을 삭제한다. 이 때 관련 댓글도 같이 삭제됨
     *
     * @param pid : 게시글 번호
     */
    @ApiOperation(value = "게시글 삭제", notes = "pid와 일치하는 게시글 삭제")
    @DeleteMapping("/api/v2/{pid}")
    public void delete(@PathVariable Long pid) {
        postService.delete(pid);
    }

    /**
     * 게시글 수정 : 해당 게시물을 수정완료 버튼을 눌렀을 때 임시저장 상태의 글은 저장완료 상태로 바뀜
     *
     * @param post
     */
    @ApiOperation(value = "게시글 수정", notes = "게시글 수정, 임시저장 글을 수정완료할 경우 상태 변경")
    @PutMapping("/api/v2")
    public void update(Post post) {
        if(post.getThumbnail() == null) post.setThumbnail(DEFAULT_THUMBNAIL_IMAGE_URL);
        postService.update(post);
    }

    /**
     * @param file
     * @param title
     * @param uid
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "섬네일 이미지 업로드", notes = "섬네일 이미지 업로드, 파일, 게시글 제목, 작성자")
    @PostMapping(value = "/api/v2/img")
    public String uploadThumbnailImages(@RequestParam("file") MultipartFile file, @RequestParam("title") String title,
                                        @RequestParam("uid") Long uid) throws Exception {

        String fullFileName = file.getOriginalFilename(); // 파일명 + 확장자
        String originFileName = fullFileName.substring(0, fullFileName.indexOf('.')); // 순수 파일명 확장자 제거
        String extension = fullFileName.substring(fullFileName.indexOf('.')); // 파일 확장자

        SHA512 filename = new SHA512(originFileName); // 파일명 SHA-512 암호화
        SHA512 sha512Title = new SHA512(title);
        String basePath = "/home/ubuntu/dist/dist/img/user/" + uid + "/" + sha512Title.getSha512(); // 루트경로 + 사용자 닉네임 + 글 제목

        File dir = new File(basePath); // 경로에 디렉토리가 존재하지 않을 경우 폴더 생성
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 디렉토리 내의 모든 파일 삭제
        File[] list = dir.listFiles();
        if(list.length != 0) {
            for (int i = 0; i < list.length; i++) {
                list[i].delete();
            }
        }


        String filePath = basePath + "/" + filename.getSha512() + extension;
        System.out.println(filePath);
        File location = new File(filePath);
        file.transferTo(location);
        String url = filePath.replace("/home/ubuntu/dist/dist/", "http://i3a507.p.ssafy.io/");

        return url;
    }


    /**
     * 사용자의 모든 임시저장 게시글을 불러옴
     * @param uid
     * @return
     */
    @ApiOperation(value = "사용자의 모든 임시저장 게시글", notes = "사용자의 모든 임시저장 게시글 불러옴")
    @GetMapping("/api/v2/temp/{uid}")
    public List<Post> findAllTempPost(@PathVariable Long uid) {
        return postService.findAllTempPost(uid);
    }

    /**
     * 사용자의 모든 게시글을 불러옴
     * @param uid
     * @return
     */
    @ApiOperation(value = "사용자의 모든 게시글", notes = "사용자의 모든 게시글 불러옴")
    @GetMapping("/api/v2/u/{uid}")
    public List<Post> findAllUserPost(@PathVariable Long uid) {
        return postService.findAllUserPost(uid);
    }


}
