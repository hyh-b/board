package com.example.board.web.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReplyResponseDto {
    private String userName;
    private String content;
}
