package com.example.board.domain.reply;

import com.example.board.domain.comment.Comment;
import com.example.board.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reply")
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "r_seq")
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "m_seq")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "c_seq")
    private Comment comment;

    @Column(name = "r_content", length = 2000)
    private String content;

    @Column(name = "r_time")
    @CreatedDate
    private LocalDateTime time;

    @Builder
    public Reply(User user, Comment comment, String content){
        this.user = user;
        this.comment = comment;
        this.content = content;
    }
}