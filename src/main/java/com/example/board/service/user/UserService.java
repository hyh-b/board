package com.example.board.service.user;

import com.example.board.domain.user.Role;
import com.example.board.domain.user.User;
import com.example.board.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User signupUser(HttpServletRequest request){
        System.out.println("서비스값"+request.getParameter("id"));
        System.out.println("서비스값"+request.getParameter("name"));
        System.out.println("서비스값"+request.getParameter("email"));
        User newUser = User.builder()
                .name(request.getParameter("name"))
                .email(request.getParameter("email"))
                .id(request.getParameter("id"))
                .password(request.getParameter("password"))
                .role(Role.USER)
                .build();

        return userRepository.save(newUser);
    }

}
