package microservices.book.multiplication.service;

import microservices.book.multiplication.challenge.ChallengeAttempt;
import microservices.book.multiplication.challenge.ChallengeAttemptDTO;
import org.springframework.stereotype.Service;

@Service
public class ChallengeServiceImpl implements ChallengeService {

    @Override
    public ChallengeAttempt verifyAttempt(ChallengeAttemptDTO challengeAttemptDTO) {
        boolean attemptIsCorrect = challengeAttemptDTO.getGuess() ==
            challengeAttemptDTO.getFactorA() * challengeAttemptDTO.getFactorB();

        return new ChallengeAttempt(null,
                null,
                challengeAttemptDTO.getFactorA(),
                challengeAttemptDTO.getFactorB(),
                challengeAttemptDTO.getGuess(), attemptIsCorrect);
    }
}
