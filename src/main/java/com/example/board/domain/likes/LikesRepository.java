package com.example.board.domain.likes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    @Query("SELECT COUNT(l) FROM Likes l WHERE l.user.seq = :mSeq AND l.posts.seq = :pSeq AND l.target = :target AND l.targetSeq = :targetSeq")
    int likeStatus(Long mSeq, Long pSeq, String target, Long targetSeq);

    @Query("SELECT l FROM Likes l WHERE l.user.seq = :mSeq AND l.posts.seq = :pSeq AND l.target = :target AND l.targetSeq = :targetSeq")
    Likes findLikes(Long mSeq, Long pSeq, String target, Long targetSeq);

    @Query("SELECT COUNT(l) FROM Likes l WHERE l.posts.seq = :pSeq AND l.target = :target AND l.targetSeq = :targetSeq")
    int countLikes(Long pSeq, String target, Long targetSeq);
}
