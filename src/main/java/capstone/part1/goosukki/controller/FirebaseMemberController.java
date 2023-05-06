package capstone.part1.goosukki.controller;

import capstone.part1.goosukki.domain.Member;
import capstone.part1.goosukki.dto.DuplicateIdRequestDto;
import capstone.part1.goosukki.dto.DuplicateResponseDto;
import capstone.part1.goosukki.service.MemberService;
import capstone.part1.goosukki.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api")
public class FirebaseMemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/members")
    public String saveMember(@RequestBody Member member) throws ExecutionException {
        return memberService.saveMember(member);
    }

    @GetMapping("/members/duplicate/id")
    public DuplicateResponseDto DuplicateId (@RequestBody DuplicateIdRequestDto requestDto) {
        return memberService.duplicateId(requestDto);
    }
}
