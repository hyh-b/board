package com.example.board.service.comment;

import com.example.board.domain.comment.Comment;
import com.example.board.domain.comment.CommentRepository;
import com.example.board.web.Dto.CommentSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    public Comment saveComment(CommentSaveRequestDto requestDto){
        return (Comment) commentRepository.save(requestDto.toEntity());
    }
}
