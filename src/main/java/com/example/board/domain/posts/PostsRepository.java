package com.example.board.domain.posts;
import org.springframework.data.jpa.repository.JpaRepository;

//mybatis에서 dao라 불리는 DB Layer접근자를 JPA에선 Repository라 부름
//JpaRepository<Entity 클래스,PK타입>을 상속하면 기본적인 CRUD메소드 자동생성
public interface PostsRepository extends JpaRepository<Posts,Long> {
}
