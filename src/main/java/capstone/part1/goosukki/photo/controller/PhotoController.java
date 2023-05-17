package capstone.part1.goosukki.photo.controller;

import capstone.part1.goosukki.photo.dto.PhotoUploadResponseDto;
import capstone.part1.goosukki.photo.service.PhotoService;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    // 특정 유저의 프로필 사진 설정
    @PostMapping("/profile")
    @ResponseStatus(value = HttpStatus.OK)
    public PhotoUploadResponseDto changeProfile (@RequestParam String memberId, @RequestParam("file")MultipartFile file) throws IOException, FirebaseAuthException, ExecutionException, InterruptedException {
        return photoService.changeProfile(memberId, file);
    }

    // 사진 고유 번호를 기준으로 사진 파일과 캡션을 리턴 (GET)

}
