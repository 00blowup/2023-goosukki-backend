package capstone.part1.goosukki.domain;

import lombok.Data;

@Data
public class Member {

    private String sequence;
    private String id;
    private String nickname;
    private String password;

    // 매개변수가 없는 public 생성자
    public Member () {}

    public Member (String id, String nickname, String password){
        this.id = id;
        this.nickname = nickname;
        this.password = password;
    }

    public String getSequence() {
        return sequence;
    }

    public String getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
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
}
