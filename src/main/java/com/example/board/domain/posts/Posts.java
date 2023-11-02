package com.example.board.domain.posts;

import com.example.board.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Getter
@NoArgsConstructor //기본 생성자 자동 추가
@Entity //db의 테이블과 링크될 클래스임을 나타냄
public class Posts extends BaseTimeEntity {   //BaseTimeEntity를 상속받아 생성, 수정시간 자동 생성

    @Id //해당 테이블의 pk필드를 나타냄
    //@GeneratedValue - pk의 생성규칙을 나타냄
    //GenerationType.IDENTITY - auto_increment를 적용
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    //@Column - 테이블의 칼럼을 나타내며 해당 클래스의 필드는 모두 칼럼이 됨
    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder // 해당 클래스의 빌더 패턴 클래스를 생성
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}
