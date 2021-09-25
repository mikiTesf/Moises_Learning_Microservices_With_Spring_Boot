package microservices.book.multiplication.service;

import microservices.book.multiplication.challenge.ChallengeAttempt;
import microservices.book.multiplication.challenge.ChallengeAttemptDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class ChallengeServiceTest {

    private ChallengeService challengeService;
    private final int FACTOR_A = 20, FACTOR_B = 20;

    @BeforeEach
    void setUp() {
        challengeService = new ChallengeServiceImpl();
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
    }
}
