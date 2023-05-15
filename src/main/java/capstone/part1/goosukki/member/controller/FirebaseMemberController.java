package capstone.part1.goosukki.member.controller;

import capstone.part1.goosukki.member.domain.Member;
import capstone.part1.goosukki.member.dto.DuplicateResponseDto;
import capstone.part1.goosukki.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/members")
public class FirebaseMemberController {

    @Autowired
    private MemberService memberService;

    // 회원가입 (POST)
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public String saveMember(@RequestBody Member member) throws ExecutionException {
        return memberService.saveMember(member);
    }

    // ID 중복여부 조회 (GET)
    @GetMapping("/duplicate/id")
    @ResponseStatus(value = HttpStatus.OK)
    public DuplicateResponseDto DuplicateId (@RequestParam String id) {
        return memberService.duplicateId(id);
    }

    // 닉네임 중복여부 조회 (GET)
    @GetMapping("/duplicate/nickname")
    @ResponseStatus(value = HttpStatus.OK)
    public DuplicateResponseDto DuplicateNickname (@RequestParam String nickname) {
        return memberService.duplicateNickname(nickname);
    }
}
