package com.example.board.web;

import com.example.board.config.auth.LoginUser;
import com.example.board.config.auth.dto.SessionUser;
import com.example.board.domain.posts.Posts;
import com.example.board.service.posts.PostsService;
import com.example.board.web.Dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user, @RequestParam(defaultValue = "0") int page){
        //model.addAttribute("posts", postsService.findAllDesc());

        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        Page<Posts> postsPage = postsService.findAll(pageable);

        int currentPage = postsPage.getNumber(); // 현재 페이지 번호 (0부터 시작)
        int totalPages = postsPage.getTotalPages(); // 총 페이지 수

        int nextPageGroupStart = Math.min((currentPage / 5 + 1) * 5, totalPages - 1);
        int prevPageGroupEnd = (currentPage / 5 - 1) * 5 + 4;

        int startPage = Math.max(0, ((postsPage.getNumber() + 1) / 5) * 5 - 1);
        int endPage = Math.min(startPage + 4, postsPage.getTotalPages() - 1);

        model.addAttribute("posts",postsPage.getContent());
        model.addAttribute("page",postsPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("nextPageGroupStart", nextPageGroupStart);
        model.addAttribute("prevPageGroupEnd", prevPageGroupEnd);
        model.addAttribute("currentPage",currentPage);
        model.addAttribute("totalPage",totalPages);

        System.out.println("숫자-=========="+postsPage.getNumber());
        System.out.println("총숫사-=========="+postsPage.getTotalPages());
        System.out.println("시작페이지-=========="+startPage);
        System.out.println("엔드페이지-=========="+endPage);
        //SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if(user != null){
            model.addAttribute("username",user.getName());
            //System.out.println("유저네임"+user.getName());
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){  //@PathVariable - url 경로에서 변수값을 추출
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post",dto);

        return "posts-update";
    }
}
