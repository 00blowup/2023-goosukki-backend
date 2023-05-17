package capstone.part1.goosukki.post.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

// 게시글 도메인
@Data
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    private String sequence;    // 게시글의 고유 순번
    private String writer;      // 작성자의 고유 순번
    private String title;       // 게시글의 제목
    private List<String> files; // 게시글에 포함된 사진&동영상들의 고유 순번 리스트
    private String latitude;    // 게시글의 위도
    private String longitude;   // 게시글의 경도
    private String placename;   // 게시글의 장소명
    private List<String> liked; // 좋아요를 누른 회원들의 고유 순번 리스트
    private Boolean secret;     // 비밀글 여부 (true면 작성자만 볼 수 있는 게시글임)

    public Post (String writer, String title, List<String> files, String latitude, String longitude, String placename, Boolean secret) {
        this.writer = writer;
        this.title = title;
        this.files = files;
        this.latitude = latitude;
        this.longitude = longitude;
        this.placename = placename;
        this.liked = new ArrayList<>();
        this.secret = secret;
    }

    // 좋아요 추가
    public void addLike (String memberSequence) {
        // 좋아요를 누른 회원의 sequence를 받아와서, liked에 추가
        liked.add(memberSequence);
    }

}
