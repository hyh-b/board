package com.example.board.web;

import com.example.board.config.auth.CustomOAuth2UserService;
import com.example.board.config.auth.CustomUserDetailService;
import com.example.board.domain.comment.Comment;
import com.example.board.domain.likes.Likes;
import com.example.board.domain.likes.LikesRepository;
import com.example.board.domain.posts.Files;
import com.example.board.domain.posts.Posts;
import com.example.board.domain.reply.Reply;
import com.example.board.domain.user.User;
import com.example.board.service.comment.CommentService;
import com.example.board.service.posts.FilesService;
import com.example.board.service.posts.LikesService;
import com.example.board.service.posts.PostsService;
import com.example.board.service.reply.ReplyService;
import com.example.board.service.user.UserService;
import com.example.board.utils.PagingUtils;
import com.example.board.web.Dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final UserService userService;
    private final FilesService filesService;
    private final LikesService likesService;
    private  final CommentService commentService;
    private final ReplyService replyService;
    @GetMapping("/")
    public String index(Model model,  @RequestParam(defaultValue = "0") int page){
        int size = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by("seq").descending());
        Page<Posts> postsPage = postsService.findAll(pageable);
        PagingUtils.PagingInfo pagingInfo = PagingUtils.calculatePagingInfo(postsPage);

        model.addAttribute("posts", postsPage.getContent());
        model.addAttribute("page", postsPage);
        model.addAttribute("pagingInfo", pagingInfo);

        User currentUser = userService.getCurrentUser();

        if(currentUser !=null){
            model.addAttribute("user", currentUser);
        }

        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    @GetMapping("/posts/view/{seq}")
    public String posts(@PathVariable Long seq,Model model){
        postsService.increaseHitCount(seq);
        User currentUser = userService.getCurrentUser();
        if(currentUser !=null){
            boolean liked = likesService.hasUserLikedPost(currentUser.getSeq(),seq, "post", seq);
            Set<Long> likedCommentSeq = commentService.getLikedCommentSeq(currentUser.getSeq(), seq);
            model.addAttribute("likedCommentSeq", likedCommentSeq);
            model.addAttribute("liked",liked);
            model.addAttribute("user", currentUser);
        }
        PostsResponseDto dto = postsService.findById(seq);
        model.addAttribute("post",dto);

        List<Files> files = filesService.loadFiles(seq);
        model.addAttribute("files",files);

        List<Comment> comment = commentService.getComment(seq);
        model.addAttribute("comment",comment);

        List<Reply> replyList = replyService.findReplyByPostSeq(seq);
        model.addAttribute("replyList", replyList);

        return "posts-view";
    }

    @GetMapping("/posts/update/{seq}")
    public String postsUpdate(@PathVariable Long seq, Model model){  //@PathVariable - url 경로에서 변수값을 추출
        User currentUser = userService.getCurrentUser();
        if(currentUser !=null){
            model.addAttribute("user", currentUser);
        }
        PostsResponseDto dto = postsService.findById(seq);
        List<Files> files = filesService.loadFiles(seq);
        model.addAttribute("files",files);
        model.addAttribute("post",dto);

        return "posts-update";
    }
}
