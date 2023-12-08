package com.example.board.web;

import com.example.board.domain.user.User;
import com.example.board.domain.user.UserRepository;
import com.example.board.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class SignInController {

    @Autowired
    private UserService userService;

    @GetMapping("signin")
    public String signin(){
        return "signin";
    }

    @GetMapping("signup")
    public String signup(){
        return "signup";
    }

    @PostMapping("/signup_ok")
    public String signup_ok(HttpServletRequest request){

        System.out.println("리퀘스트"+request.getParameter("id"));
        System.out.println("리퀘스트"+request.getParameter("name"));
        System.out.println("리퀘스트"+request.getParameter("password"));
        userService.signupUser(request);
        return "signin";
    }
}
