package com.side.moneymoriapi.service.concurrency;

import com.side.moneymoriapi.dto.member.LoginMemberDto.LoginMemberRequestDto;
import com.side.moneymoriapi.mapper.member.MemberCommendMapper;
import com.side.moneymoriapi.mapper.member.MemberQueryMapper;
import com.side.moneymoriapi.projection.member.MemberProjection.MemberUsernamePasswordProjection;
import com.side.moneymoriapi.redis.domain.RefreshToken;
import com.side.moneymoriapi.redis.repository.RefreshTokenRepository;
import com.side.moneymoriapi.service.MemberService;
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
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class MemberServiceConcurrencyTest {

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
    void loginMember_concurrentAccess_withStrictValidation() throws InterruptedException {
        // Arrange
        LoginMemberRequestDto requestDto = new LoginMemberRequestDto(username, rawPassword);
        MemberUsernamePasswordProjection projection = new MemberUsernamePasswordProjection(memberId, username, encodedPassword);

        when(queryMapper.findByUsername(username)).thenReturn(Optional.of(projection));
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);
        when(jwtProvider.createToken(eq(username), any())).thenReturn("accessToken");
        when(random.nextString()).thenReturn("refreshToken");
        when(serverTime.nowInstant()).thenReturn(Instant.now());

        Map<String, String> refreshTokenStore = new ConcurrentHashMap<>();
        doAnswer(invocation -> {
            RefreshToken refreshToken = invocation.getArgument(0);
            refreshTokenStore.put(refreshToken.getSubject(), refreshToken.getRefreshToken());
            return null;
        }).when(refreshTokenRepository).save(any());

        int threadCount = 100;
        int totalExecutions = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(totalExecutions);

        Callable<Void> task = () -> {
            try {
                memberService.loginMember(requestDto);
            } finally {
                latch.countDown();
            }
            return null;
        };

        IntStream.range(0, totalExecutions).forEach(i -> executorService.submit(task));

        latch.await();
        executorService.shutdown();

        // Assert
        assertThat(refreshTokenStore).hasSize(1); // username은 하나이므로 refreshToken은 한 개만 있어야 함
        assertThat(refreshTokenStore.get(username)).isNotNull();

        verify(queryMapper, times(totalExecutions)).findByUsername(username);
        verify(refreshTokenRepository, times(totalExecutions)).save(any());
    }


    @Test
    void loginMember_concurrentAccess_withStatusLogging() throws InterruptedException {
        // Arrange
        LoginMemberRequestDto requestDto = new LoginMemberRequestDto(username, rawPassword);
        MemberUsernamePasswordProjection projection = new MemberUsernamePasswordProjection(memberId, username, encodedPassword);

        when(queryMapper.findByUsername(username)).thenReturn(Optional.of(projection));
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);
        when(jwtProvider.createToken(eq(username), any())).thenReturn("accessToken");
        when(random.nextString()).thenReturn("refreshToken");
        when(serverTime.nowInstant()).thenReturn(Instant.now());

        Map<String, String> refreshTokenStore = new ConcurrentHashMap<>();
        doAnswer(invocation -> {
            RefreshToken refreshToken = invocation.getArgument(0);
            System.out.println("Saving refreshToken: " + refreshToken.getRefreshToken());
            refreshTokenStore.put(refreshToken.getSubject(), refreshToken.getRefreshToken());
            System.out.println("Current refreshTokenStore: " + refreshTokenStore);
            return null;
        }).when(refreshTokenRepository).save(any());

        int threadCount = 100;
        int totalExecutions = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(totalExecutions);

        Callable<Void> task = () -> {
            try {
                System.out.println("Thread " + Thread.currentThread().getName() + " started.");
                memberService.loginMember(requestDto);
                System.out.println("Thread " + Thread.currentThread().getName() + " completed.");
            } finally {
                latch.countDown();
            }
            return null;
        };

        IntStream.range(0, totalExecutions).forEach(i -> executorService.submit(task));

        latch.await();
        executorService.shutdown();

        // Assert
        assertThat(refreshTokenStore).hasSize(1); // username은 하나이므로 refreshToken은 한 개만 있어야 함
        verify(queryMapper, times(totalExecutions)).findByUsername(username);
        verify(refreshTokenRepository, times(totalExecutions)).save(any());
    }

}