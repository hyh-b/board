package com.example.board.service.posts;

import com.example.board.domain.posts.Posts;
import com.example.board.domain.posts.PostsRepository;
import com.example.board.web.Dto.PostsListResponseDto;
import com.example.board.web.Dto.PostsResponseDto;
import com.example.board.web.Dto.PostsSaveRequestDto;
import com.example.board.web.Dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
//스프링에서 bean을 주입받는 방식은 @Autowired, setter, 생성자 이렇게 3가지이다
//이 중 가장 권장하는 방식은 생성자로 주입받는 방식
//@RequiredArgsConstructor를 이용해 final이 선언된 필드에 생성자 자동생성
    private final PostsRepository postsRepository;

    @Transactional   //메서드의 시작과 끝에 트랜젝션을 시작하고 종료/ 트랜잭션 관리
    public Long save(PostsSaveRequestDto requestDto){

        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new
                IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)  //(readOnly = true)옵션 - 조회기능만 남겨둔다. 그에 따라 조회속도 개선
    public List<PostsListResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream() //stream - 자바8버전부터 생긴 반복문 병렬 처리기법.
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    //PostsRepository결과로 넘어온 Posts의 Stream을 map을 통해 PostListResponseDto 변환 -> List로 반환하는 메서드
    }

    @Transactional
    public void delete (Long id){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다. id="+ id));

        postsRepository.delete(posts); //JpaRepository에서 delete 메소드를 지원
    }

    public Page<Posts> findAll(Pageable pageable){
        return postsRepository.findAll(pageable);
    }
}
