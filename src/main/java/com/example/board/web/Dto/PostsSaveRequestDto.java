package com.example.board.web.Dto;

import com.example.board.domain.posts.Posts;
import com.example.board.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor //모든 필드에 기본 생성자를 생성
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private User user;
    @Builder
    public PostsSaveRequestDto(String title, String content){

        this.title = title;
        this.content = content;

    }

    public Posts toEntity(){
        return Posts.builder()
                .title(title)
                .content(content)
                .user(user)
                .build();
    }
}
