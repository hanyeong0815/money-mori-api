package com.side.moneymoriapi.api.member;

import com.side.moneymoriapi.domain.member.Member;
import com.side.moneymoriapi.dto.member.CreateMemberDto.CreateMemberRequestDto;
import com.side.moneymoriapi.usecase.member.CreateMemberUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/member")
@RequiredArgsConstructor
public class MemberCommendApi {
    private final CreateMemberUseCase createMemberUseCase;

    @PostMapping("")
    public ResponseEntity<Member> createMember(@RequestBody CreateMemberRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createMemberUseCase.createMember(dto));
    }
}
