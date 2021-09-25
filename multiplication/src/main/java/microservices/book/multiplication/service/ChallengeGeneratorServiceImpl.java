package microservices.book.multiplication.service;

import lombok.RequiredArgsConstructor;
import microservices.book.multiplication.challenge.Challenge;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class ChallengeGeneratorServiceImpl implements ChallengeGeneratorService {

    private final Random random;
    private final int MIN_FACTOR = 11;
    private final int MAX_FACTOR = 100;

    ChallengeGeneratorServiceImpl() {
        this.random = new Random();
    }

    private int next() {
        return random.nextInt(MAX_FACTOR - MIN_FACTOR) + MIN_FACTOR;
    }

    @Override
    public Challenge randomChallenge() {
        return new Challenge(next(), next());
    }
}
