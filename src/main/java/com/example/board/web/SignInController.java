package com.example.board.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class SignInController {

    @GetMapping("signin")
    public String signin(){
        return "signin";
    }

    @GetMapping("signup")
    public String signup(){
        return "signup";
    }
}
