package capstone.part1.goosukki.controller;

import capstone.part1.goosukki.domain.Member;
import capstone.part1.goosukki.dto.DuplicateResponseDto;
import capstone.part1.goosukki.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/members")
public class FirebaseMemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public String saveMember(@RequestBody Member member) throws ExecutionException {
        return memberService.saveMember(member);
    }

    @GetMapping("/duplicate/id")
    @ResponseStatus(value = HttpStatus.OK)
    public DuplicateResponseDto DuplicateId (@RequestParam String id) {
        return memberService.duplicateId(id);
    }

    @GetMapping("/duplicate/nickname")
    @ResponseStatus(value = HttpStatus.OK)
    public DuplicateResponseDto DuplicateNickname (@RequestParam String nickname) {
        return memberService.duplicateNickname(nickname);
    }
}
