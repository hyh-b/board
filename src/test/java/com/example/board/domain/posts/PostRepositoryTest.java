/*
package com.example.board.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @AfterEach //Junit에서 단위 테스트가 끝날때마다 수행되는 메소드를 지정
    public void cleanup(){
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기(){
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        //postsRepository.save - 테이블 posts에 insert/updata쿼리를 실행
        //id값이 있다면 update, 없다면 insert쿼리가 실행돤다.
        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("jojoid@gmail.com")
                .build());

        //when
        //postsRepository.findAll - 테이블 posts에 있는 모든 데이터를 조회해오는 메소드
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity_등록(){
        //given
        LocalDateTime now = LocalDateTime.of(2019,6,4,0,0,0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);

        System.out.println(">>>>>>>>>> createDate="+posts.getCreatedDate()+
                ", ModifiedDate="+posts.getModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(now); //CreatedDate가 now보다 뒤인지, 즉 now에 저장된 시간보다 생성시간이 뒤인지 확인
        assertThat(posts.getModifiedDate()).isAfter(now); //ModifiedDate가 now보다 뒤인지 확인
    }
}
*/
