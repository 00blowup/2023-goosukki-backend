package capstone.part1.goosukki.photo.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PhotoUploadResponseDto {
    Long sequence;    // 파이어베이스 Firestore Database에 저장된 파일 고유 번호
    String fileName;    // 파이어베이스 스토리지에 저장된 파일명
    String fileLocation;    // 파이어베이스 스토리지 상의 파일 위치

    public PhotoUploadResponseDto (Long sequence, String fileName, String fileLocation) {
        this.sequence = sequence;
        this.fileName = fileName;
        this.fileLocation = fileLocation;
    }
}
