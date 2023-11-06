package com.example.board.config.auth;

import com.example.board.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver  implements HandlerMethodArgumentResolver {

    private final HttpSession httpSession;

    @Override
    public boolean supportsParameter(MethodParameter parameter){
    // supportsParameter - 컨트롤러 메서드의 특정 파라미터를 지원하는지 판단합니다
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        // 파라미터에 @LoginUser 어노테이션이 붙어있는지 확인
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());
        // 파라미터 클래스타입이 SessionUser.class인지 확인
        return isLoginUserAnnotation && isUserClass;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer
            , NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception{
    // resolveArgument - 파라미터에 전달할 객체를 생성 / 여기서는 세션에서 객체를 가져옴
        return httpSession.getAttribute("user");
    }
}
