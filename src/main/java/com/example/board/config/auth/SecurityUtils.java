package com.example.board.config.auth;

import com.example.board.config.auth.dto.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static CustomUserDetails getCurrentUserDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        /*if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())){
            return  null;
        }

        Object principal = authentication.getPrincipal();
        if(principal instanceof CustomUserDetails){
            return (CustomUserDetails) principal;
        }*/

        return null;
    }
}
