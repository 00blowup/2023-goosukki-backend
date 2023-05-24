package capstone.part1.goosukki.post.dto;

import capstone.part1.goosukki.post.domain.Post;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class PostRecentResponseDto {
    private String writerNickname;  // 작성자 닉네임
    private String writerPhoto; // 작성자의 프로필사진 파일명
    private String title;   // 게시글 제목
    private String placeName;   // 장소명
    private List<String> filenames;  // 사진들의 파일명 리스트
    private List<String> captions;  // 캡션들 리스트

    public PostRecentResponseDto(String writerNickname, String writerPhoto, Post post, List<String> filenames, List<String> captions) {
        this.writerNickname = writerNickname;
        this.writerPhoto = writerPhoto;
        this.title = post.getTitle();
        this.placeName = post.getPlacename();
        this.filenames = filenames;
        this.captions = captions;
    }

}
