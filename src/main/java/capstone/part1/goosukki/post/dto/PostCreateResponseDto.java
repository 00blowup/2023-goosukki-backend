package capstone.part1.goosukki.post.dto;

import capstone.part1.goosukki.post.domain.Post;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PostCreateResponseDto {
    private String sequence;    // 게시글의 고유 순번
    private String writer;      // 작성자의 고유 순번
    private String title;       // 게시글의 제목
    private List<String> files; // 게시글에 포함된 사진&동영상들의 고유 순번 리스트
    private Double latitude;    // 게시글의 위도
    private Double longitude;   // 게시글의 경도
    private String placename;   // 게시글의 장소명
    private Boolean secret;     // 비밀글 여부 (true면 작성자만 볼 수 있는 게시글임)

    public PostCreateResponseDto (Post post) {
        this.sequence = post.getSequence();
        this.writer = post.getWriter();
        this.title = post.getTitle();
        this.files = post.getFiles();
        this.latitude = post.getLatitude();
        this.longitude = post.getLongitude();
        this.placename = post.getPlacename();
        this.secret = post.getSecret();
    }
}
