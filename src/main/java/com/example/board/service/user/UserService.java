package com.example.board.service.user;

import com.example.board.domain.user.Role;
import com.example.board.domain.user.User;
import com.example.board.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    public void printUser(String id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            System.out.println("아이디"+user.getId());
            System.out.println("비번"+user.getPassword());
            System.out.println("소셜"+user.getSocial());
        } else {
            System.out.println("User not found with id: " + id);
        }
    }



}