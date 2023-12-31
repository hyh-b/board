package com.example.board.web;

import com.example.board.domain.comment.Comment;
import com.example.board.domain.posts.Posts;
import com.example.board.domain.user.User;
import com.example.board.service.comment.CommentService;
import com.example.board.service.posts.PostsService;
import com.example.board.service.user.UserService;
import com.example.board.web.Dto.CommentSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class CommentApiController {
    private final CommentService commentService;
    private final UserService userService;
    private final PostsService postsService;

    @PostMapping("/api/v1/comment/save")
    public ResponseEntity<?> saveComment(@RequestBody CommentSaveRequestDto requestDto) {
        Comment newComment = commentService.saveComment(requestDto);
        return ResponseEntity.ok(newComment);
    }

    @PatchMapping("/api/v1/comment/count/{pSeq}")
    public ResponseEntity<?> countComment(@PathVariable Long pSeq){
        postsService.updateCommentCount(pSeq);
        return ResponseEntity.ok().build();
    }
}
