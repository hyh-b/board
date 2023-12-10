package com.example.board.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor  //기본 생성자 생성
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "member")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //@GeneratedValue - pk키 생성 및 방법 지정
    @Column(name = "m_seq")
    private Long seq;

    @Column(name = "m_name", nullable = false)
    private String name;

    @Column(name = "m_email", nullable = false)
    private String email;

    @Enumerated(EnumType.STRING) //@Enumerated - JPA로 데이터베이스를 저장할 때 Enum 값을 어떤 형태로 저장할지를 결정
    @Column(name = "m_role", nullable = false)
    private Role role;

    @Column(name = "m_social")
    private String social;

    @Column(name = "m_joindate")
    @CreatedDate
    private LocalDateTime joindate;

    @Column(name = "m_id")
    private String id;

    @Column(name = "m_password")
    private String password;

    @Builder
    public User(String name, String email, Role role, String social,String id, String password){
        this.name = name;
        this.email = email;
        this.role = role;
        this.social = social;
        this.id = id;
        this.password = password;
    }

    public  User update(String name, String social){
        this.name = name;
        this.social = social;

        return this;
    }

    public  String getRoleKey(){
        return this.role.getKey();
    }
}
