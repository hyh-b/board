package com.example.board.service.posts;

import com.example.board.domain.likes.Likes;
import com.example.board.domain.likes.LikesRepository;
import com.example.board.domain.posts.Posts;
import com.example.board.domain.posts.PostsRepository;
import com.example.board.web.Dto.LikesSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final LikesRepository likesRepository;
    private final PostsRepository postsRepository;

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
        int count = likesRepository.countLikes(pSeq, "post", pSeq);
        Posts post = postsRepository.findById(pSeq)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + pSeq));
        post.setLike(count);
        postsRepository.save(post);
    }
}
