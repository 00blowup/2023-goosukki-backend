package capstone.part1.goosukki.photo.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PhotoUploadResponseDto {
    Long sequence;    // 파이어베이스 Firestore Database에 저장된 파일 고유 번호

    public PhotoUploadResponseDto (Long sequence) {
        this.sequence = sequence;
    }
}
