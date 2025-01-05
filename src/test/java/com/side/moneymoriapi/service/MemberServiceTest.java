package com.side.moneymoriapi.service;

import com.side.moneymoriapi.dto.member.CreateMemberDto.CreateMemberRequestDto;
import com.side.moneymoriapi.dto.member.LoginMemberDto.LoginMemberRequestDto;
import com.side.moneymoriapi.dto.member.LoginMemberDto.LoginMemberResponseDto;
import com.side.moneymoriapi.mapper.member.MemberCommendMapper;
import com.side.moneymoriapi.mapper.member.MemberQueryMapper;
import com.side.moneymoriapi.projection.member.MemberProjection.MemberUsernamePasswordProjection;
import com.side.moneymoriapi.redis.repository.RefreshTokenRepository;
import com.side.moneymoriapi.utils.jwt.JwtProvider;
import com.side.moneymoriapi.utils.random.StrongStringRandom;
import com.side.moneymoriapi.utils.time.ServerTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberCommendMapper commendMapper;

    @Mock
    private MemberQueryMapper queryMapper;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private StrongStringRandom random;

    @Mock
    private ServerTime serverTime;

    private final String username = "test_user";
    private final String rawPassword = "password123";
    private final String encodedPassword = "$2a$12$encryptedpassword";
    private final UUID memberId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createMember_success() {
        // Arrange
        CreateMemberRequestDto requestDto = new CreateMemberRequestDto(username, rawPassword, "test@example.com");
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);

        // Act
        memberService.createMember(requestDto);

        // Assert
        verify(passwordEncoder, times(1)).encode(rawPassword);
        verify(commendMapper, times(1)).createMember(any());
        verify(commendMapper, times(1)).insertRoles(any(), any());
    }

    @Test
    void loginMember_success() {
        // Arrange
        LoginMemberRequestDto requestDto = new LoginMemberRequestDto(username, rawPassword);
        MemberUsernamePasswordProjection projection = new MemberUsernamePasswordProjection(memberId, username, encodedPassword);

        when(queryMapper.findByUsername(username)).thenReturn(Optional.of(projection));
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);
        when(jwtProvider.createToken(eq(username), any())).thenReturn("accessToken");
        when(random.nextString()).thenReturn("refreshToken");
        when(serverTime.nowInstant()).thenReturn(Instant.now());

        // Act
        LoginMemberResponseDto response = memberService.loginMember(requestDto);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.username()).isEqualTo(username);
        assertThat(response.tokenPair().accessToken()).isEqualTo("accessToken");
        assertThat(response.tokenPair().refreshToken()).isEqualTo("refreshToken");

        verify(queryMapper, times(1)).findByUsername(username);
        verify(passwordEncoder, times(1)).matches(rawPassword, encodedPassword);
        verify(refreshTokenRepository, times(1)).save(any());
    }

    @Test
    void loginMember_invalidCredentials() {
        // Arrange
        LoginMemberRequestDto requestDto = new LoginMemberRequestDto(username, rawPassword);

        when(queryMapper.findByUsername(username)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> memberService.loginMember(requestDto));

        verify(queryMapper, times(1)).findByUsername(username);
        verify(passwordEncoder, never()).matches(any(), any());
        verify(jwtProvider, never()).createToken(any(), any());
    }
}
