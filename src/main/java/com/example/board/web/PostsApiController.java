package com.example.board.web;

import com.example.board.service.posts.PostsService;
import com.example.board.web.Dto.PostsResponseDto;
import com.example.board.web.Dto.PostsSaveRequestDto;
import com.example.board.web.Dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;
    //REST에서 CRUD는 다음과 같이 HTTP Method에 매핑 - 생성:POST, 읽기:GET, 수정:PUT, 삭제:DELETE
    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto){

        return postsService.save(requestDto);
    }

    //업데이트 작업은 일반적으로 Put메소드를 사용하므로 PutMapping 사용
    @PutMapping("/api/v1/posts/{id}")          //@PathVariable - url경로에서 변수값을 추출한 뒤 할당
    public Long update(@PathVariable Long id,    //여기선 {id}값을 추출해 Long id에 할당함
                       @RequestBody PostsUpdateRequestDto requestDto){
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id){

        return postsService.findById(id);
    }

    @DeleteMapping("api/v1/posts/{id}")
    public Long delete(@PathVariable Long id){
        postsService.delete(id);
        return id;
    }

}
