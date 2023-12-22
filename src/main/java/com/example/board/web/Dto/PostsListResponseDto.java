package com.example.board.web.Dto;

import com.example.board.domain.posts.Posts;
import com.example.board.domain.user.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsListResponseDto {
    private Long seq;
    private String title;
    private User user;
    private LocalDateTime modifiedDate;

    public PostsListResponseDto(Posts entity){
        this.seq = entity.getSeq();
        this.title = entity.getTitle();
        this.user = entity.getUser();
    }

}
