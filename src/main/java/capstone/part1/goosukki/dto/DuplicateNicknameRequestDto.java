package capstone.part1.goosukki.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DuplicateNicknameRequestDto {
    String nickname;

    @Builder
    public DuplicateNicknameRequestDto (String nickname) {
        this.nickname = nickname;
    }
}
