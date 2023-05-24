package capstone.part1.goosukki.post.controller;

import capstone.part1.goosukki.photo.service.PhotoService;
import capstone.part1.goosukki.post.dto.PostCreateRequestDto;
import capstone.part1.goosukki.post.dto.PostCreateResponseDto;
import capstone.part1.goosukki.post.dto.PostRecentResponseDto;
import capstone.part1.goosukki.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private PhotoService photoService;

    // 게시글 추가(POST)
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(value = HttpStatus.CREATED)
    public PostCreateResponseDto addPost(@RequestPart PostCreateRequestDto requestDto, @RequestPart List<String> captions, @RequestPart("files") MultipartFile[] files) throws IOException, ExecutionException, InterruptedException {
        for(String i : captions) System.out.println(i);
        // 보내준 파일들을 파이어베이스에 저장하고, 저장된 파일들의 sequence 리스트를 받아오기
        List<String> fileSequences = photoService.uploadPostsPhotos(files, captions, requestDto);
        return postService.addPost(fileSequences, requestDto);
    }

    // 비둘기 터치 시 가장 최근 게시글의 내용을 Firebase에서 꺼내와서 유니티에게 줌
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public PostRecentResponseDto recentPost() {
        //PostService의 recent 함수를 실행하여 게시글의 제목, 사진 리스트, 캡션 리스트, 위치명, 작성자 닉네임, 작성자 프로필을 담은 DTO를 리턴
        return postService.recent();
    }

}
