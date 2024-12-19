package com.side.moneymoriapi.api.member;

import com.side.moneymoriapi.dto.member.LoginMemberDto.LoginMemberRequestDto;
import com.side.moneymoriapi.dto.member.LoginMemberDto.LoginMemberResponseDto;
import com.side.moneymoriapi.usecase.member.LoginMemberUseCase;
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
public class MemberQueryApi {
    private final LoginMemberUseCase loginMemberUseCase;

    @PostMapping("login")
    public ResponseEntity<LoginMemberResponseDto> login(@RequestBody LoginMemberRequestDto dto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(loginMemberUseCase.loginMember(dto));
    }
}
