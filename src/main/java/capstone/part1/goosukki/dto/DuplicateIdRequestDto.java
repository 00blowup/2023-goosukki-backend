package capstone.part1.goosukki.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DuplicateIdRequestDto {
    String id;

    @Builder
    public DuplicateIdRequestDto (String id) {
        this.id = id;
    }
}
