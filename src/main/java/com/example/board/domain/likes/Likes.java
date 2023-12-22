package com.example.board.domain.likes;

import com.example.board.domain.posts.Posts;
import com.example.board.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "likes")
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "l_seq")
    private Integer seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "m_seq")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "p_seq")
    private Posts posts;

    @Column(name = "l_target")
    private String target;

    @Column(name = "l_target_seq")
    private Long targetSeq;


    @Builder
    public Likes(User user, Posts posts, String target, Long targetSeq){
        this.user = user;
        this.posts = posts;
        this.target = target;
        this.targetSeq = targetSeq;
    }

}
