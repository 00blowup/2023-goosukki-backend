package capstone.part1.goosukki.photo.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
public class Photo {

    // 고유 번호
    private String sequence;
    // 파일명
    private String filename;
    // 캡션
    private String caption;

    public Photo (Long sequence, String filename) {
        this.sequence = sequence.toString();
        this.filename = filename;
        this.caption = null;
    }
    public Photo (Long sequence, String filename, String caption) {
        this.sequence = sequence.toString();
        this.filename = filename;
        this.caption = caption;
    }


    public void setSequence(String sequence) {
        this.sequence = sequence;
    }
    public void setCaption(String caption) {
        this.caption = caption;
    }
}
