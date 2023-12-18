package com.example.board.config.auth;

import com.example.board.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity   // spring security기능 활성화
@Configuration      // 이 클래스가 spring의 구성클래스임을 나타냄 / 이 클래스가 의존성 주입 컨테이너에 빈을 정의하고 제공하는 역할
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    private final CustomUserDetailService customUserDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .headers().frameOptions().disable() //h2-console 화면을 사용하기 위해 해당 옵션들을 disable
                .and()
                    .authorizeRequests() //URL별 권환 관리 설정
                    /*.antMatchers("/","/css/**","/images/**","/js/**","/h2-console/**","/profile").permitAll()
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())*/
                    .anyRequest().permitAll()
                .and()
                    .formLogin()
                        .loginPage("/signin")
                        .loginProcessingUrl("/signin_ok")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/signin?error")
                .and()
                    .logout()
                    .logoutSuccessUrl("/")
                .and()
                    .oauth2Login() //oauth2 로그인 기능 설정
                        //.defaultSuccessUrl("/", true)
                        .userInfoEndpoint() //로그인 성공 이후 사용자 정보를 가져올 때의 설정을 담당
                            .userService(customOAuth2UserService); // 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록. 리소스 서버(즉, 소설 서비스)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시

        return http.build();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(customUserDetailService)
                .passwordEncoder(passwordEncoder);
    }
}
