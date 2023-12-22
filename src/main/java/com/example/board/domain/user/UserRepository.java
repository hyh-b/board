package com.example.board.domain.user;

import com.example.board.domain.posts.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //Optional - 자바 8에서 생긴 기능으로 NullPointerException을 방지하기 위한 래퍼 클래스이다.
    //값의 존재여부를 표현하며 값이 없는 경우에 대비한 로직 처리가능
    Optional<User> findByEmail(String email);  //소셜 로그인으로 반환되는 값 중 email을 통해 이미 생선된 사용자인지
                                               //처음 가입하는 사용자인지 판단하기 위한 메소드

    @Query("select m from User m where m.id = ?1")
    Optional<User> findById(String id);

    @Query("select m from User m where m.socialid = ?1")
    Optional<User> findBySocialid(String socialid);

}
