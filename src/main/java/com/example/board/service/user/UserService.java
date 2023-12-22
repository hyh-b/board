package com.example.board.service.user;

import com.example.board.config.auth.CustomOAuth2UserService;
import com.example.board.config.auth.CustomUserDetailService;
import com.example.board.domain.user.Role;
import com.example.board.domain.user.User;
import com.example.board.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomUserDetailService customUserDetailService;

    public User signupUser(HttpServletRequest request){

        User newUser = User.builder()
                .name(request.getParameter("name"))
                .email(request.getParameter("email"))
                .id(request.getParameter("id"))
                .password(passwordEncoder.encode(request.getParameter("password")))
                .social("")
                .role(Role.USER)
                .build();

        return userRepository.save(newUser);
    }
    public UserService(CustomUserDetailService customUserDetailService, CustomOAuth2UserService customOAuth2UserService){
        this.customOAuth2UserService = customOAuth2UserService;
        this.customUserDetailService = customUserDetailService;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            OAuth2User oAuth2User = (OAuth2User) oauthToken.getPrincipal();
            String registrationId = oauthToken.getAuthorizedClientRegistrationId();
            return customOAuth2UserService.loadUser(oAuth2User, registrationId);
        } else if (authentication.getPrincipal() instanceof UserDetails) {
            return (User) customUserDetailService.loadUser(authentication.getName());
        }

        return null; // 혹은 적절한 예외 처리
    }
}
