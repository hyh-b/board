package com.example.board.web.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplySaveRequestDto {

    Long userSeq;
    Long commentSeq;
    String content;

}
