package microservices.book.multiplication.service;

import microservices.book.multiplication.challenge.ChallengeAttempt;
import microservices.book.multiplication.challenge.ChallengeAttemptDTO;
import microservices.book.multiplication.challenge.ChallengeAttemptRepository;
import microservices.book.multiplication.user.User;
import microservices.book.multiplication.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ChallengeServiceTest {

    private ChallengeService challengeService;
    private final int FACTOR_A = 20, FACTOR_B = 20;

    /**
     * This repository is declared with the 'lenient' parameter is in order for `checkExistingUserTest` to
     * execute without throwing an UnnecessaryStubbingException exception. The mock behaviour defined in the
     * `setUp` method is important for the other tests in the class but not for this specific method. The same
     * reasoning goes for the `challengeAttemptRepository` mock and the test method `checkRecentAttemptTest`.
     */
    @Mock(lenient = true)
    private UserRepository userRepository;

    @Mock(lenient = true)
    private ChallengeAttemptRepository challengeAttemptRepository;

    @BeforeEach
    void setUp() {
        challengeService = new ChallengeServiceImpl(challengeAttemptRepository, userRepository);
        // mock behaviour setup
        given(challengeAttemptRepository.save(any())).will(returnsFirstArg());
        given(userRepository.save(any())).will(returnsFirstArg());
    }

    @Test
    void checkWrongAttemptTest() {
        // given
        ChallengeAttemptDTO wrongAttempt =
                new ChallengeAttemptDTO(FACTOR_A, FACTOR_B, "retard", 30);
        // when
        ChallengeAttempt attemptResult =
                challengeService.verifyAttempt(wrongAttempt);
        // then
        then(attemptResult.isCorrect()).isFalse();
    }

    @Test
    void checkCorrectAttemptTest() {
        // given
        ChallengeAttemptDTO correctAttempt =
                new ChallengeAttemptDTO(FACTOR_A, FACTOR_B, "genius", 400);
        // when
        ChallengeAttempt attemptResult =
                challengeService.verifyAttempt(correctAttempt);
        // then
        then(attemptResult.isCorrect()).isTrue();
        verify(userRepository).save(new User("genius"));
        verify(challengeAttemptRepository).save(attemptResult);
    }

    @Test
    public void checkExistingUserTest() {
        // given
        User existingUser = new User(1L, "john_doe");
        given(userRepository.findByAlias("john_doe"))
                .willReturn(Optional.of(existingUser));
        ChallengeAttemptDTO attemptDTO =
                new ChallengeAttemptDTO(50, 60, "john_doe", 5000);
        // when
        ChallengeAttempt resultAttempt =
                challengeService.verifyAttempt(attemptDTO);
        // then
        then(resultAttempt.isCorrect()).isFalse();
        then(resultAttempt.getUser()).isEqualTo(existingUser);
        verify(userRepository, never()).save(any());
        verify(challengeAttemptRepository).save(resultAttempt);
    }

    @Test
    public void checkRecentAttemptTest() {
        // given
        final String alias = "john_doe";
        User johnDoe = new User(alias);
        List<ChallengeAttempt> recentAttempts = Arrays.asList(
                new ChallengeAttempt(null, johnDoe, 10, 10, 10, false),
                new ChallengeAttempt(null, johnDoe, 10, 10, 10, false)
        );
        given(challengeAttemptRepository.findTop10ByUserAliasOrderByIdDesc(anyString())).willReturn(recentAttempts);
        // when
        List<ChallengeAttempt> result = challengeService.getLast10Attempts(alias);
        // then
        then(result).isEqualTo(recentAttempts);
    }
}
