package com.example.board.web.Dto;

import com.example.board.domain.posts.Files;
import com.example.board.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilesSaveRequestDto {
    private Long seq;
    private Posts posts;
    private String name;
    private String path;

    @Builder
    public FilesSaveRequestDto(Posts posts,String name, String path){
        this.posts = posts;
        this.name = name;
        this.path = path;
    }

    public Files toEntity(){
        return Files.builder()
                .posts(posts)
                .name(name)
                .path(path)
                .build();
    }
}
