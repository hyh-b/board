package com.example.board.web.Dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor //final이 붙은 필드에 생성자를 자동으로 생성
public class HelloResponseDto {
    private final String name;
    private final int amount;


}
