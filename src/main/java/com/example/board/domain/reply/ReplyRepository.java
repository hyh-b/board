package com.example.board.domain.reply;

import com.example.board.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Query("SELECT r FROM Reply r JOIN r.comment c WHERE c.posts.seq = :pSeq")
    List<Reply> findReplyByPostSeq(Long pSeq);
}
