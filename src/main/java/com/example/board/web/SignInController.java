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
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class SignInController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("signin")
    public String signin(@RequestParam(value = "error", required = false) String error){
        if (error != null){
            return "index";
        }
        return "signin";
    }

    @GetMapping("signup")
    public String signup(){

        return "signup";
    }

    @GetMapping("signok")
    public String signok(){

        return "signok";
    }

    @PostMapping("/signup_ok")
    public String signup_ok(HttpServletRequest request){

        userService.signupUser(request);
        return "signin";
    }
}
