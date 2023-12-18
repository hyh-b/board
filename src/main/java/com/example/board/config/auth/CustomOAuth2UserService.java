package com.example.board.config.auth;

import com.example.board.config.auth.dto.OAuthAttributes;
import com.example.board.config.auth.dto.SessionUser;
import com.example.board.domain.user.User;
import com.example.board.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); //로그인 진행중인 서비스를 구분하는 코드. 어디소셜인지

        String userNameAttributeName = userRequest  //oauth2 로그인 진행시 키가 되는 필드값. pk키와 같은 의미
                .getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes  //OAuth2Service를 통해 가져온 OAuth2User의 attribute를 담을 클래스
                .of(registrationId, oAuth2User.getAttributes());
        System.out.println("소셜로그인>>>>>>>>>>");

        User user = saveOrUpdate(attributes);

        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }


    public User loadUser(OAuth2User oAuth2User, String registrationId) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String socialId;

        switch (registrationId) {
            case "naver":
                socialId = (String) attributes.get("id");
                break;
            case "kakao":
                Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
                socialId = String.valueOf(attributes.get("id"));
                break;
            case "google":
                socialId = (String) attributes.get("sub");
                break;
            default:
                throw new IllegalArgumentException("Unsupported registration id: " + registrationId);
        }

        User user = userRepository.findBySocialid(socialId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with social id: " + socialId));

        return user;
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(),attributes.getSocial()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
