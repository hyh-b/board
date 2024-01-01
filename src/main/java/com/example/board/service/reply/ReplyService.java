package com.example.board.service.reply;

import com.example.board.domain.comment.Comment;
import com.example.board.domain.reply.Reply;
import com.example.board.domain.reply.ReplyRepository;
import com.example.board.domain.user.User;
import com.example.board.service.comment.CommentService;
import com.example.board.service.user.UserService;
import com.example.board.web.Dto.ReplyResponseDto;
import com.example.board.web.Dto.ReplySaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final UserService userService;
    private final CommentService commentService;

    public ReplyResponseDto saveReply(ReplySaveRequestDto requestDto){
        User user = userService.getCurrentUser();
        Comment comment = commentService.findById(requestDto.getCommentSeq());
        Reply reply = Reply.builder()
                .user(user)
                .comment(comment)
                .content(requestDto.getContent())
                .build();

        replyRepository.save(reply);
        return convertToDto(reply);
    }

    private ReplyResponseDto convertToDto(Reply reply) {
        ReplyResponseDto dto = new ReplyResponseDto();
        dto.setUserName(reply.getUser().getName()); // 지연 로딩 문제를 피하기 위해 필요한 데이터만 사용
        dto.setContent(reply.getContent());
        return dto;
    }

    public List<Reply> findReplyByPostSeq(Long pSeq){
        return replyRepository.findReplyByPostSeq(pSeq);
    }
}
