package com.example.board.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor  //기본 생성자 생성
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //@GeneratedValue - pk키 생성 및 방법 지정
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING) //@Enumerated - JPA로 데이터베이스를 저장할 때 Enum 값을 어떤 형태로 저장할지를 결정
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String name, String email, String picture, Role role){
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public  User update(String name, String picture){
        this.name = name;
        this.picture = picture;

        return this;
    }

    public  String getRoleKey(){
        return this.role.getKey();
    }
}
