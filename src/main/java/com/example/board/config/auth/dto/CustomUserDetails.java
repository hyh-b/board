package com.example.board.config.auth.dto;

import com.example.board.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Getter
@Setter
public class CustomUserDetails implements UserDetails{
    private User user;

    private Long seq;
    private String name;
    private String email;
    private String role;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(User user){
        this.user = user;
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    }


    @Override
    public String getPassword(){
        return user.getPassword();
    }

    @Override
    public String getUsername(){
        return user.getId();
    }

    // 계정이 만료되지 않았는지 확인
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠겨있지 않은지 확인
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호가 만료되지 않았는지 확인
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정이 활성화 되어있는지 확인
    @Override
    public boolean isEnabled() {
        return true;
    }


}
