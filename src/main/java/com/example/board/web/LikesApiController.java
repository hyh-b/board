package com.example.board.web;

import com.example.board.domain.comment.CommentRepository;
import com.example.board.domain.likes.Likes;
import com.example.board.domain.likes.LikesRepository;
import com.example.board.domain.posts.Posts;
import com.example.board.domain.user.User;
import com.example.board.service.comment.CommentService;
import com.example.board.service.posts.LikesService;
import com.example.board.service.posts.PostsService;
import com.example.board.service.user.UserService;
import com.example.board.web.Dto.LikesSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
@RestController
public class LikesApiController {
    private final LikesService likesService;
    private final PostsService postsService;
    private final UserService userService;
    private final CommentService commentService;
    private final CommentRepository commentRepository;

    @GetMapping("/api/v1/likes/{postSeq}/{userSeq}")
    public ResponseEntity<?> checkUserLike(@PathVariable Long postSeq, @PathVariable Long userSeq){
        boolean liked = likesService.hasUserLikedPost(userSeq,postSeq,"post",postSeq);
        return ResponseEntity.ok(Collections.singletonMap("liked", liked));
    }

    @PostMapping("/api/v1/likes")
    public ResponseEntity<?> saveLike(@RequestBody LikesSaveRequestDto requestDto){
        User currentUser = userService.getCurrentUser();
        if(currentUser !=null){
            requestDto.setUser(currentUser);
        }

        Posts posts = postsService.getPostById(requestDto.getPostSeq());
        requestDto.setPosts(posts);

        try {
            Likes savedLike = likesService.saveLike(requestDto);
            return ResponseEntity.ok(savedLike);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error saving like");
        }
    }

    @DeleteMapping("/api/v1/likes/{pSeq}/{target}/{targetSeq}")
    public ResponseEntity<?> deleteLike(@PathVariable Long pSeq, @PathVariable String target, @PathVariable Long targetSeq){
        User currentUser = userService.getCurrentUser();
        likesService.deleteLike(currentUser.getSeq(), pSeq, target, targetSeq);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/v1/likes/update/{pSeq}")
    public ResponseEntity<?> updateLikesCount(@PathVariable Long pSeq) {
        likesService.updateLikesCount(pSeq);
        int likeCount = postsService.findById(pSeq).getLike();

        return ResponseEntity.ok(likeCount);
    }

    @GetMapping("/api/v1/likes/comment/update/{pSeq}/{commentSeq}")
    public ResponseEntity<?> updateCommentLikesCount(@PathVariable Long pSeq, @PathVariable Long commentSeq) {
        likesService.updateCommentLikesCount(pSeq, commentSeq);
        int likeCount = commentService.findById(commentSeq).getLike();

        return ResponseEntity.ok(likeCount);
    }
}
