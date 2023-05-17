package capstone.part1.goosukki.post.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PostCreateRequestDto {

    private String writer;
    private String title;
    private String latitude;
    private String longitude;
    private String placename;
    private Boolean secret;

}
