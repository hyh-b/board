package com.example.board.web.Dto;

import com.example.board.domain.likes.Likes;
import com.example.board.domain.posts.Posts;
import com.example.board.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikesSaveRequestDto {
    private Posts posts;
    private User user;
    private String target;
    private Long targetSeq;
    private Long postSeq;

    @Builder
    public LikesSaveRequestDto(User user, Posts posts, String target, Long targetSeq){
        this.user = user;
        this.posts = posts;
        this.target = target;
        this.targetSeq = targetSeq;
    }

    public Likes toEntity(){
        return Likes.builder()
                .user(user)
                .posts(posts)
                .target(target)
                .targetSeq(targetSeq)
                .build();
    }
}
