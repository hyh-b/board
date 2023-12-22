package com.example.board.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FilesRepository extends JpaRepository<Files,Long> {
    @Query("select f from Files f where f.posts.seq = ?1")
    List<Files> findByPseq(Long seq);
}
