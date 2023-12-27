package com.example.board.web.Dto;

import com.example.board.domain.comment.Comment;
import com.example.board.domain.posts.Posts;
import com.example.board.domain.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentSaveRequestDto {

    private Long postSeq;
    private String content;

}
