package com.example.board.web;

import com.example.board.domain.posts.Posts;
import com.example.board.domain.user.User;
import com.example.board.service.comment.CommentService;
import com.example.board.service.posts.PostsService;
import com.example.board.service.user.UserService;
import com.example.board.web.Dto.CommentSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentApiController {
    private final CommentService commentService;
    private final UserService userService;
    private final PostsService postsService;

    @PostMapping("/api/v1/comment/{postSeq}")
    public ResponseEntity<?> saveComment( @PathVariable Long postSeq, @RequestBody CommentSaveRequestDto requestDto) {
        User currentUser = userService.getCurrentUser();
        requestDto.setUser(currentUser);
        Posts posts = postsService.getPostById(postSeq);
        requestDto.setPosts(posts);
        commentService.saveComment(requestDto);
        return ResponseEntity.ok().build();
    }
}
