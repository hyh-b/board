package com.example.board.service.reply;

import com.example.board.domain.comment.Comment;
import com.example.board.domain.reply.Reply;
import com.example.board.domain.reply.ReplyRepository;
import com.example.board.domain.user.User;
import com.example.board.service.comment.CommentService;
import com.example.board.service.user.UserService;
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

    public Reply saveReply(ReplySaveRequestDto requestDto){
        User user = userService.getCurrentUser();
        Comment comment = commentService.findById(requestDto.getCommentSeq());

        Reply reply = Reply.builder()
                .user(user)
                .comment(comment)
                .content(requestDto.getContent())
                .build();

        replyRepository.save(reply);
        return reply;
    }

    public List<Reply> findReplyByPostSeq(Long pSeq){
        return replyRepository.findReplyByPostSeq(pSeq);
    }
}
