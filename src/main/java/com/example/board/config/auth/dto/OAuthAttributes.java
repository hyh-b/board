package com.example.board.config.auth.dto;

import com.example.board.domain.user.Role;
import com.example.board.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String social;
    private String socialid;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey,
                           String name, String email, String social, String socialid) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.social = social;
        this.socialid = socialid;
    }

    public static OAuthAttributes of(String registrationId,
                                     Map<String, Object> attributes) {
        if ("naver".equals(registrationId)){
            System.out.println("<<<<<<<<네이버로그인");
            return ofNaver("id",attributes);
        }
        else if ("kakao".equals(registrationId)){
            System.out.println("<<<<<<<<카카오로그인");
            return odKakao("id",attributes);
        }
        else if ("google".equals(registrationId)){
            System.out.println("<<<<<<<<구글로그인");
            return ofGoogle("sub",attributes);
        }
        throw new IllegalArgumentException("Unsupported registrationId: " + registrationId);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName,
                                            Map<String, Object> attributes) {

        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .social("google")
                .socialid((String) attributes.get("sub"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes){
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .social("naver")
                .socialid((String) response.get("id"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public static OAuthAttributes odKakao(String userNameAttributeName, Map<String, Object> attributes){

        Map<String, Object> response = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) response.get("profile");
        String userId = String.valueOf(attributes.get("id"));

        return OAuthAttributes.builder()
                .name((String) profile.get("nickname"))
                .email((String) response.get("email"))
                .social("kakao")
                .socialid(userId)
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity() {  //처음 가입할 때 User 엔티티 생성
        return User.builder()
                .name(name)
                .email(email)
                .role(Role.USER)
                .social(social)
                .socialid(socialid)
                .build();
    }
}
