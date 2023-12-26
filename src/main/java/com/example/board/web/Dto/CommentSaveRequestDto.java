package com.example.board.web.Dto;

import com.example.board.domain.comment.Comment;
import com.example.board.domain.posts.Posts;
import com.example.board.domain.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentSaveRequestDto {

    private Posts posts;
    private User user;
    private String content;

    public Comment toEntity(){
        return Comment.builder()
                .posts(posts)
                .user(user)
                .content(content)
                .build();
    }
}
