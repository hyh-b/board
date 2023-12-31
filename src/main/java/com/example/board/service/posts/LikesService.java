package com.example.board.service.posts;

import com.example.board.domain.comment.Comment;
import com.example.board.domain.comment.CommentRepository;
import com.example.board.domain.likes.Likes;
import com.example.board.domain.likes.LikesRepository;
import com.example.board.domain.posts.Posts;
import com.example.board.domain.posts.PostsRepository;
import com.example.board.web.Dto.LikesSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final LikesRepository likesRepository;
    private final PostsRepository postsRepository;
    private final CommentRepository commentRepository;

    public boolean hasUserLikedPost(Long userSeq, Long postSeq, String target, Long targetSeq) {

        return likesRepository.likeStatus(userSeq, postSeq, target, targetSeq) > 0;
    }

    public Likes saveLike(LikesSaveRequestDto dto){
        return likesRepository.save(dto.toEntity());
    }

    public void deleteLike(Long mSeq, Long pSeq, String target, Long targetSeq){
        Likes likes = likesRepository.findLikes(mSeq,pSeq,target,targetSeq);

        likesRepository.delete(likes);
    }

    public void updateLikesCount(Long pSeq) {

        postsRepository.updateLikesCount(pSeq,"post",pSeq);
    }

    public void updateCommentLikesCount(Long pSeq, Long targetSeq) {

        commentRepository.updateLikesCount(pSeq,"comment",targetSeq);
    }


}
