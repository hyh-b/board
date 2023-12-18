package com.example.board.web;

import com.example.board.config.auth.CustomOAuth2UserService;
import com.example.board.config.auth.CustomUserDetailService;
import com.example.board.config.auth.LoginUser;
import com.example.board.config.auth.SecurityUtils;
import com.example.board.config.auth.dto.CustomUserDetails;
import com.example.board.config.auth.dto.OAuthAttributes;
import com.example.board.config.auth.dto.SessionUser;
import com.example.board.domain.posts.Posts;
import com.example.board.domain.user.User;
import com.example.board.domain.user.UserRepository;
import com.example.board.service.posts.PostsService;
import com.example.board.web.Dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;
    @Autowired
    private final UserRepository userRepository;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomUserDetailService customUserDetailService;

    @GetMapping("/")
    public String index(Model model,  @RequestParam(defaultValue = "0") int page){
        //model.addAttribute("posts", postsService.findAllDesc());
        int size = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by("Id").descending());
        Page<Posts> postsPage = postsService.findAll(pageable);
        int currentPage = postsPage.getNumber(); // 현재 페이지 번호 (0부터 시작)
        int totalPages = postsPage.getTotalPages(); // 총 페이지 수
        int countPage = (currentPage)/5;
        int countEnd = (totalPages-1)/5;

        int nextPageGroupStart = countPage*5+5;
        int prevPageGroupEnd = countPage*5-1;

        int startPage = countPage*5 +1;
        int endPage = startPage+4;
        model.addAttribute("posts",postsPage.getContent());
        model.addAttribute("page",postsPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("nextPageGroupStart", nextPageGroupStart);
        model.addAttribute("prevPageGroupEnd", prevPageGroupEnd);
        model.addAttribute("currentPage",currentPage);
        model.addAttribute("totalPage",totalPages);
        model.addAttribute("countEnd",countEnd);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            System.out.println("테스트ㅡㅡㅡㅡㅡ");
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                System.out.println("권한이 뭐냐: " + authority.getAuthority());
            }
        }

        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            String registrationId = oauthToken.getAuthorizedClientRegistrationId();
            OAuth2User oAuth2User = (OAuth2User) oauthToken.getPrincipal();
            User user = customOAuth2UserService.loadUser(oAuth2User, registrationId);
            model.addAttribute("user",user);
            // user 객체의 정보 출력
            System.out.println("User Name: " + user.getName());
            System.out.println("User Email: " + user.getEmail());
            System.out.println("User social: " + user.getSocialid());
            System.out.println("User seq: " + user.getSeq());
            System.out.println("User role: " + user.getRole());

        } else if(authentication.getPrincipal() instanceof UserDetails) {
            User user = (User) customUserDetailService.loadUser(authentication.getName());
            model.addAttribute("user",user);
            System.out.println("일반유저에스이큐"+user.getSeq());
            System.out.println("일반유저이메일"+user.getEmail());
            System.out.println("일반유저이름"+user.getName());
            System.out.println("일반롤"+user.getRole());
        }else{
            System.out.println("No OAuth2 authenticated user found");
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    @GetMapping("/posts/view")
    public String posts(){
        return "posts-view";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){  //@PathVariable - url 경로에서 변수값을 추출
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post",dto);

        return "posts-update";
    }
}
