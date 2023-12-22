package com.example.board.domain.posts;

import com.example.board.domain.BaseTimeEntity;
import com.example.board.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor //기본 생성자 자동 추가
@Entity //db의 테이블과 링크될 클래스임을 나타냄
@EntityListeners(AuditingEntityListener.class)
@Table(name = "posts")
public class Posts {   //BaseTimeEntity를 상속받아 생성, 수정시간 자동 생성

    //@Id //해당 테이블의 pk필드를 나타냄
    //@GeneratedValue - pk의 생성규칙을 나타냄
    //GenerationType.IDENTITY - auto_increment를 적용
    //@Column - 테이블의 칼럼을 나타내며 해당 클래스의 필드는 모두 칼럼이 됨


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "p_seq")
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "m_seq")
    private User user;

    @Column(name = "p_title")
    private String title;

    @Column(name = "p_content")
    private String content;

    @Column(name = "p_hit")
    private Integer hit = 0;

    @Column(name = "p_like")
    private Integer like = 0;

    @Column(name = "p_comment")
    private Integer comment = 0;

    @Column(name = "p_time")
    @CreatedDate
    private LocalDateTime time;

    @Builder // 해당 클래스의 빌더 패턴 클래스를 생성
    public Posts(String title, String content, User user){
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}
