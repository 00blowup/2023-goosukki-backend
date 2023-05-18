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
    private Double latitude;    // 게시글의 위도
    private Double longitude;   // 게시글의 경도
    private String placename;   // 게시글의 장소명
    private List<String> love; // 하트를 누른 회원들의 고유 순번 리스트 (1)
    private List<String> like; // 따봉을 누른 회원들의 고유 순번 리스트 (2)
    private List<String> wow; // 놀라워요를 누른 회원들의 고유 순번 리스트 (3)
    private List<String> fun; // 웃겨요를 누른 회원들의 고유 순번 리스트 (4)
    private Boolean secret;     // 비밀글 여부 (true면 작성자만 볼 수 있는 게시글임)

    public Post (String writer, String title, List<String> files, Double latitude, Double longitude, String placename, Boolean secret) {
        this.writer = writer;
        this.title = title;
        this.files = files;
        this.latitude = latitude;
        this.longitude = longitude;
        this.placename = placename;
        this.love = new ArrayList<>();
        this.like = new ArrayList<>();
        this.wow = new ArrayList<>();
        this.fun = new ArrayList<>();
        this.secret = secret;
    }

    // 반응 추가
    public void addLike (String memberSequence, Integer which) {
        // 좋아요를 누른 회원의 sequence를 받아와서, liked에 추가

    }

}
