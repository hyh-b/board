package com.example.board.web;

import com.example.board.config.auth.CustomOAuth2UserService;
import com.example.board.config.auth.CustomUserDetailService;
import com.example.board.domain.posts.Posts;
import com.example.board.domain.user.User;
import com.example.board.service.posts.FilesService;
import com.example.board.service.posts.PostsService;
import com.example.board.service.user.UserService;
import com.example.board.web.Dto.PostsResponseDto;
import com.example.board.web.Dto.PostsSaveRequestDto;
import com.example.board.web.Dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;
    private final UserService userService;
    private final FilesService filesService;
    //REST에서 CRUD는 다음과 같이 HTTP Method에 매핑 - 생성:POST, 읽기:GET, 수정:PUT, 삭제:DELETE
    @PostMapping(path = "/api/v1/posts")
    @Transactional
    public ResponseEntity<?> save(@ModelAttribute PostsSaveRequestDto requestDto, @RequestParam(value = "mfiles", required = false)MultipartFile[] files){
        User currentUser = userService.getCurrentUser();
        if(currentUser !=null){
            requestDto.setUser(currentUser);
        }

        Posts posts = postsService.save(requestDto);
        if(files != null) {
            filesService.processAndStoreFiles(files, posts);
        }
        return ResponseEntity.ok().build();
    }


    @PutMapping("/api/v1/posts/{seq}")
    @Transactional
    public ResponseEntity<?> update(@PathVariable Long seq, @ModelAttribute PostsUpdateRequestDto requestDto,
                                    @RequestParam(value = "newFiles", required = false)MultipartFile[] newFiles
                                    , @RequestParam(value = "deletedFiles", required = false)List<Long> deletedFiles) {
        Posts posts = postsService.getPostById(seq);
        postsService.update(seq, requestDto);

        if(newFiles != null){
            filesService.processAndStoreFiles(newFiles,posts);
        }

        if(deletedFiles != null && !deletedFiles.isEmpty()){
            for(Long fileSeq : deletedFiles){
                filesService.deleteFile(fileSeq);
            }
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/v1/posts/{seq}")
    public ResponseEntity<PostsResponseDto> findById(@PathVariable Long seq){
        PostsResponseDto post;

        post = postsService.findById(seq);
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("api/v1/posts/{seq}")
    public Long delete(@PathVariable Long seq){
        User currentUser = userService.getCurrentUser();
        Long postSeq = postsService.getPostById(seq).getUser().getSeq();

        if(currentUser.getSeq()== postSeq){
            postsService.delete(seq);
        }
        return seq;
    }

}
