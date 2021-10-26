package microservices.book.multiplication.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservices.book.multiplication.challenge.ChallengeAttempt;
import microservices.book.multiplication.challenge.ChallengeAttemptDTO;
import microservices.book.multiplication.challenge.ChallengeAttemptRepository;
import microservices.book.multiplication.user.User;
import microservices.book.multiplication.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChallengeServiceImpl implements ChallengeService {

    private final ChallengeAttemptRepository challengeAttemptRepository;
    private final UserRepository userRepository;
    private final ChallengeEventPublisher challengeEventPublisher;

    @Override
    public ChallengeAttempt verifyAttempt(ChallengeAttemptDTO challengeAttemptDTO) {
        User user = userRepository.findByAlias(challengeAttemptDTO.getUserAlias())
                .orElseGet(() -> {
                    log.info("Creating a new User with the alias: {}", challengeAttemptDTO.getUserAlias());
                    return userRepository.save(new User(challengeAttemptDTO.getUserAlias()));
                });

        boolean attemptIsCorrect = challengeAttemptDTO.getGuess() ==
            challengeAttemptDTO.getFactorA() * challengeAttemptDTO.getFactorB();

        ChallengeAttempt attempt = new ChallengeAttempt(null,
                user,
                challengeAttemptDTO.getFactorA(),
                challengeAttemptDTO.getFactorB(),
                challengeAttemptDTO.getGuess(),
                attemptIsCorrect);

        attempt = challengeAttemptRepository.save(attempt);

        // Publish the attempt to notify potentially interested subscribers
        challengeEventPublisher.publishChallengeSolvedEvent(attempt);

        return attempt;
    }

    @Override
    public List<ChallengeAttempt> getLast10Attempts(String userAlias) {
        return challengeAttemptRepository.findTop10ByUserAliasOrderByIdDesc(userAlias);
    }
}
