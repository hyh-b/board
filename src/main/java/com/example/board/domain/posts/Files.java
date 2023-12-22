package com.example.board.domain.posts;

import com.example.board.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "files")
public class Files {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "f_seq")
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "p_seq")
    private Posts posts;

    @Column(name = "f_name")
    private String name;

    @Column(name = "f_path")
    private String path;

    @Builder // 해당 클래스의 빌더 패턴 클래스를 생성
    public Files(Posts posts, String name, String path){
        this.posts = posts;
        this.name = name;
        this.path = path;
    }

}