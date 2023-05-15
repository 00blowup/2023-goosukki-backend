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

    public Photo (Long sequence, String filename) {
        this.sequence = sequence.toString();
        this.filename = filename;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }
}
