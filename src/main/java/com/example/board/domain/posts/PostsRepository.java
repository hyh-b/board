package com.example.board.domain.posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//mybatis에서 dao라 불리는 DB Layer접근자를 JPA에선 Repository라 부름
//JpaRepository<Entity 클래스,PK타입>을 상속하면 기본적인 CRUD메소드 자동생성
// 주의!
// Entity 클래스와 기본 Entity Repository는 함께 위치해야 하는 점.
// 둘은 아주 밀접한 관계이고, Entity 클래스는 기본 Repository 없이는 제대로 역할을 할 수 가 없다.
// 나중에 프로젝트 규모가 커져 도메인별로 프로젝트를 분리해야 한다면 이때 Entity 클래스와 기본 Repository는 함께 움직여야 하므로 도메인 패키지에 함께 관리하게 된다.
public interface PostsRepository extends JpaRepository<Posts,Long> {

    @Query("SELECT p from Posts p order by  p.seq desc ")
    List<Posts> findAllDesc();

    Page<Posts> findAll(Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Posts p SET p.comment = (SELECT COUNT(c) FROM Comment c WHERE c.posts.seq = :pSeq) WHERE p.seq = :pSeq")
    void updateCommentCount(Long pSeq);

    @Transactional
    @Modifying
    @Query("UPDATE Posts p SET p.like = (SELECT COUNT(l) FROM Likes l WHERE l.posts.seq = :pSeq AND l.target = :target AND l.targetSeq = :targetSeq) WHERE p.seq = :pSeq")
    void updateLikesCount(Long pSeq, String target, Long targetSeq);

}
