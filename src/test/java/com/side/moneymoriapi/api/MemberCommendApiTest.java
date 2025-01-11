package com.side.moneymoriapi.api;

import com.side.moneymoriapi.api.member.MemberCommendApi;
import com.side.moneymoriapi.common.exception_handler.GlobalExceptionHandler;
import com.side.moneymoriapi.vo.member.Member;
import com.side.moneymoriapi.vo.type.RoleType;
import com.side.moneymoriapi.dto.member.CreateMemberDto.CreateMemberRequestDto;
import com.side.moneymoriapi.usecase.member.CreateMemberUseCase;
import com.side.moneymoriapi.utils.time.ServerTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, MockitoExtension.class})
@WebMvcTest(MemberCommendApi.class)
@AutoConfigureRestDocs
public class MemberCommendApiTest {
    @MockitoBean
    private CreateMemberUseCase createMemberUseCase;

    @MockitoBean
    private ServerTime serverTime;

    @MockitoBean
    private GlobalExceptionHandler globalExceptionHandler;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createMemberTest() throws Exception {
        // Given
        CreateMemberRequestDto requestDto = new CreateMemberRequestDto("username", "password", "email");

        Member responseEntity = new Member(UUID.randomUUID(), "username", "password", "email", Instant.now(), null, List.of(RoleType.USER));

        Mockito.when(createMemberUseCase.createMember(Mockito.any(CreateMemberRequestDto.class)))
                .thenReturn(responseEntity);

        // When & Then
        mockMvc.perform(
                post("/api/member")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "username": "username",
                            "password": "password",
                            "email": "email"
                        }
                        """)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("username"))
                .andExpect(jsonPath("$.password").value("password"))
                .andExpect(jsonPath("$.email").value("email"))
//                .andExpect(jsonPath("$.roles").value(List.of(RoleType.USER)))
                .andDo(document("member-signin",
                        requestFields(
                                fieldWithPath("username").description("The username of the new member"),
                                fieldWithPath("password").description("The password of the new member"),
                                fieldWithPath("email").description("The Email of the new member")
                        ),
                        responseFields(
                                fieldWithPath("id").description("Created Member's id"),
                                fieldWithPath("username").description("Created Member's username"),
                                fieldWithPath("password").description("Created Member's password"),
                                fieldWithPath("email").description("Created Member's email"),
                                fieldWithPath("createdAt").description("When Created Member"),
                                fieldWithPath("updatedAt").description("When Updated Member"),
                                fieldWithPath("roles").description("Created Member's roles")
                        )
                ));
    }
}
