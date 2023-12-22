package com.example.board.web.Dto;

import com.example.board.domain.posts.Posts;
import com.example.board.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostsResponseDto {

    private Long seq;
    private String title;
    private String content;
    private User user;
    private LocalDateTime time;
    private Integer hit;
    private Integer like;
    private Integer comment;

    public PostsResponseDto(Posts entity){
        this.seq = entity.getSeq();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.user = entity.getUser();
        this.time = entity.getTime();
        this.hit = entity.getHit();
        this.like = entity.getLike();
        this.comment = entity.getComment();
    }
}
