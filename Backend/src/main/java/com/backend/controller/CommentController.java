package com.backend.controller;

import com.backend.dto.comment.Comment;
import com.backend.service.CommentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"*"})
public class CommentController {

    @Autowired
    CommentService commentService;

    /**
     * 포스트에 댓글 작성
     * @param comment : 댓글 등록
     * @return 성공적으로 댓글이 등록된 경우 201 반환
     */
    @ApiOperation(value = "댓글 작성", notes = "포스트에 댓글 작성")
    @PostMapping("/api/v3/")
    public HttpStatus save(@RequestBody Comment comment) {
        commentService.save(comment);
        return HttpStatus.CREATED;
    }

    /**
     * 포스트의 댓글의 대댓글 작성
     * @param comment
     * @return
     */
    @ApiOperation(value = "대댓글 작성", notes = "댓글에 대댓글 작성")
    @PostMapping("/api/v3/r")
    public HttpStatus saveReply(@RequestBody Comment comment) {
        commentService.saveReply(comment);
        return HttpStatus.CREATED;
    }


    /**
     * 댓글 삭제 하위 대댓글도 모두 삭제됨
     * @param cid : 댓글 고유 아이디
     */
    @ApiOperation(value = "댓글 삭제", notes = "댓글 삭제시 대댓글도 삭제")
    @DeleteMapping("/api/v3/{cid}")
    public void delete(@PathVariable Long cid) {
        commentService.delete(cid);
    }


    /**
     * 댓글 수정
     * @param comment
     */
    @ApiOperation(value = "댓글 수정", notes = "댓글 수정")
    @PutMapping("/api/v3")
    public void update(@RequestBody Comment comment) {
        commentService.update(comment);
    }

    /**
     * pid 에 해당하는 포스트의 모든 댓글들을 다시 읽어온다.
     * @param pid
     * @return
     */
    @ApiOperation(value = "포스트의 모든 댓글 읽어옴", notes = "댓글 갱신을 위한 모든 댓글 리로드")
    @GetMapping("/api/v3/{pid}")
    public List<Comment> findAllComment(@PathVariable Long pid) {
        return commentService.findAllCommentsInPost(pid);
    }
}
