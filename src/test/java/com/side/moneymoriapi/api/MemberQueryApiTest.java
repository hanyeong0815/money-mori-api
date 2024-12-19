package com.side.moneymoriapi.api;

import com.side.moneymoriapi.api.member.MemberQueryApi;
import com.side.moneymoriapi.common.exception_handler.GlobalExceptionHandler;
import com.side.moneymoriapi.dto.member.LoginMemberDto.JwtTokenPair;
import com.side.moneymoriapi.dto.member.LoginMemberDto.LoginMemberRequestDto;
import com.side.moneymoriapi.dto.member.LoginMemberDto.LoginMemberResponseDto;
import com.side.moneymoriapi.usecase.member.LoginMemberUseCase;
import com.side.moneymoriapi.utils.time.ServerTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, MockitoExtension.class})
@WebMvcTest(MemberQueryApi.class)
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
public class MemberQueryApiTest {

    @MockBean
    private LoginMemberUseCase loginMemberUseCase;

    @MockBean
    private ServerTime serverTime;

    @MockBean
    private GlobalExceptionHandler globalExceptionHandler;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void loginMemberTest() throws Exception {
        // Given
        LoginMemberRequestDto requestDto = new LoginMemberRequestDto("username", "password");

        JwtTokenPair tokenPair = JwtTokenPair.builder()
                .accessToken("mockAccessToken")
                .refreshToken("mockRefreshToken")
                .build();

        LoginMemberResponseDto responseDto = LoginMemberResponseDto.builder()
                .username("username")
                .tokenPair(tokenPair)
                .build();

        Mockito.when(loginMemberUseCase.loginMember(Mockito.any(LoginMemberRequestDto.class)))
                .thenReturn(responseDto);

        // When
        mockMvc.perform(post("/api/member/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username": "test",
                                    "password": "test123"
                                }
                                """))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.username").value("username"))
                .andExpect(jsonPath("$.tokenPair.accessToken").value("mockAccessToken"))
                .andExpect(jsonPath("$.tokenPair.refreshToken").value("mockRefreshToken"))
                .andDo(document("member-login",
                        requestFields(
                                fieldWithPath("username").description("The username of the member."),
                                fieldWithPath("password").description("The password of the member.")
                        ),
                        responseFields(
                                fieldWithPath("username").description("The username of the logged-in member."),
                                fieldWithPath("tokenPair.accessToken").description("The generated access token."),
                                fieldWithPath("tokenPair.refreshToken").description("The generated refresh token.")
                        )
                ));
    }
}