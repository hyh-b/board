package com.example.board.web;

import com.example.board.domain.reply.Reply;
import com.example.board.service.reply.ReplyService;
import com.example.board.web.Dto.ReplyResponseDto;
import com.example.board.web.Dto.ReplySaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ReplyApiController {

    private final ReplyService replyService;

    @PostMapping("/api/v1/reply/save")
    public ResponseEntity<?> saveReply(@RequestBody ReplySaveRequestDto requestDto) {
        ReplyResponseDto replyDto = replyService.saveReply(requestDto);
        return ResponseEntity.ok(replyDto);
    }
}
