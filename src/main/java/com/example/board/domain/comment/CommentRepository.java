package com.example.board.domain.comment;

import com.example.board.domain.posts.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Query("SELECT c from Comment c where c.posts.seq = :postSeq order by c.seq asc ")
    List<Comment> findAllAsc(Long postSeq);
}
