package com.side.moneymoriapi.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberCommendApiTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createMemberTest() throws Exception {
        // Given
        String memberJson = """
                {
                    "username": "test",
                    "password": "test123",
                    "email": "test@test.com"
                }
                """;

        // When & Then
        mockMvc.perform(
                post("/api/member")
                .contentType(MediaType.APPLICATION_JSON)
                .content(memberJson)
        ).andExpect(status().isCreated());
    }
}
