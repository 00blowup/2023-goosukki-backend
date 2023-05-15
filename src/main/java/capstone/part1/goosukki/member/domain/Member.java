package capstone.part1.goosukki.member.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
public class Member {

    private String sequence;
    private String id;
    private String nickname;
    private String password;
    // 프로필사진의 고유 번호
    private String photo;

    public Member (String id, String nickname, String password){
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.photo = "0";
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoto(Long photo) {
        this.photo = photo.toString();
    }
}
