package capstone.part1.goosukki.controller;

import capstone.part1.goosukki.domain.Member;
import capstone.part1.goosukki.dto.DuplicateIdRequestDto;
import capstone.part1.goosukki.dto.DuplicateNicknameRequestDto;
import capstone.part1.goosukki.dto.DuplicateResponseDto;
import capstone.part1.goosukki.service.MemberService;
import capstone.part1.goosukki.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/members")
public class FirebaseMemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("")
    public String saveMember(@RequestBody Member member) throws ExecutionException {
        return memberService.saveMember(member);
    }

    @GetMapping("/duplicate/id")
    public DuplicateResponseDto DuplicateId (@RequestBody DuplicateIdRequestDto requestDto) {
        return memberService.duplicateId(requestDto);
    }

    @GetMapping("/duplicate/nickname")
    public DuplicateResponseDto DuplicateNickname (@RequestBody DuplicateNicknameRequestDto requestDto) {
        return memberService.duplicateNickname(requestDto);
    }
}
