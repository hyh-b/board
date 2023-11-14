package com.example.board.web;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;

@WebMvcTest(controllers = HelloController.class)// Web(Spring MVC)에 집중할 수 있는 어노테이션이다. 선언할 경우 @Controller, @ControllerAdvice등을 사용할 수 있다. 단, @Service, @Component, @Respository등은 사용할 수 없다.
@MockBean(JpaMetamodelMappingContext.class)
@ExtendWith(SpringExtension.class)
public class HelloControllerTest {

    @Autowired // 스프링 빈(Bean)을 주입
    private MockMvc mvc;  // 개발한 웹 프로그램을 실제 서버에 배포하지 않고도 테스트를 위한 요청을 제공 즉, 웹 API 테스트 할 때 사용, 이 클래스를 통해 HTTP GET, POST등에 대한 API테스트


    @Test
    public void hello가_리턴된다() throws Exception{
        String hello = "hello";
        //spring security를 적용하기 전
        /*mvc.perform(get("/hello")) //해당 주소로 get요청을 보낸다
                .andExpect(status().isOk()) //mvc.perform의 결과를 검증한다/HTTP Status를 검증한다(200,400,500 등)/isOk는 200인지 아닌지 확인
                .andExpect(content().string(hello)); //mvc.perform의 결과검증/응답 본문의 내용을 검증/ Controller에서 hello를 리턴하기 때문에 이 값이 맞는지 검증

         */
        //spring security를 적용한 후
        mvc.perform(get("/hello").with(oauth2Login()))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

    @Test
    public void helloDto가_리턴된다() throws Exception{
        String name = "hello";
        int amount = 1000;
        // param = API테스트할 때 사용될 요청 파라미터를 설정, 값은 String만 허용. 숫자일 경우 문자열로 변경해야함
        //spring security를 적용하기 전
        /*mvc.perform(
                get("/hello/dto")
                        .param("name",name)  //name과 amount의 값을 엔드포인트로 보냄
                        .param("amount",String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is(name)))
                .andExpect(jsonPath("$.amount",is(amount)));
        */
        //spring security를 적용한 후
        mvc.perform(get("/hello/dto").with(oauth2Login()).param("name", name).param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));

    }
}
