package capstone.part1.goosukki.member.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DuplicateResponseDto {
    Boolean success;

    public DuplicateResponseDto(Boolean success) {
        this.success = success;
    }
}
