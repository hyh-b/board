package com.example.board.domain.comment;

import com.example.board.domain.posts.Posts;
import com.example.board.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.event.EventListener;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_seq")
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "p_seq")
    private Posts posts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "m_seq")
    private User user;

    @Column(name = "c_content", length = 2000)
    private String content;

    @Column(name = "c_time")
    @CreatedDate
    private LocalDateTime time;

    @Column(name = "c_like")
    private Integer like = 0;

    @Builder
    public Comment(Posts posts, User user, String content){
        this.posts = posts;
        this.user = user;
        this.content = content;
    }
}
