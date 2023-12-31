package com.example.board.service.comment;

import com.example.board.domain.comment.Comment;
import com.example.board.domain.comment.CommentRepository;
import com.example.board.domain.posts.Posts;
import com.example.board.domain.user.User;
import com.example.board.service.posts.PostsService;
import com.example.board.service.user.UserService;
import com.example.board.web.Dto.CommentSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostsService postsService;
    private final UserService userService;
    public Comment saveComment(CommentSaveRequestDto requestDto){
        Posts posts = postsService.getPostById(requestDto.getPostSeq());
        User user = userService.getCurrentUser();

        Comment comment = Comment.builder()
                .posts(posts)
                .user(user)
                .content(requestDto.getContent())
                .build();

        commentRepository.save(comment);
        return comment;
    }

    public List<Comment> getComment(Long postSeq){
        return commentRepository.findAllAsc(postSeq);
    }

    public Set<Long> getLikedCommentSeq(Long userSeq, Long postSeq) {
        List<Long> likedCommentSeq = commentRepository.findLikedComments(userSeq, postSeq);
        return new HashSet<>(likedCommentSeq);
    }

    public Comment findById(Long cSeq){
        Comment comment = commentRepository.findById(cSeq)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. id=" + cSeq));
        return comment;
    }

}
