package com.example.board.domain.comment;

import com.example.board.domain.posts.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Query("SELECT c from Comment c where c.posts.seq = :postSeq order by c.seq asc ")
    List<Comment> findAllAsc(Long postSeq);

    @Query("SELECT c.seq FROM Comment c JOIN Likes l ON c.seq = l.targetSeq AND c.posts.seq = l.posts.seq WHERE l.target = 'comment' AND l.user.seq = :userSeq AND c.posts.seq = :postSeq")
    List<Long> findLikedComments(Long userSeq, Long postSeq);

    @Transactional
    @Modifying
    @Query("UPDATE Comment c SET c.like = (SELECT COUNT(l) FROM Likes l WHERE l.posts.seq = :pSeq AND l.target = :target AND l.targetSeq = :targetSeq) WHERE c.seq = :targetSeq")
    void updateLikesCount(Long pSeq, String target, Long targetSeq);
}
