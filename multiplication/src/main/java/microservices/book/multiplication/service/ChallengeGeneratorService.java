package microservices.book.multiplication.service;

import microservices.book.multiplication.challenge.Challenge;

public interface ChallengeGeneratorService {
    /**
     * @return a randomly generated challenge with factors between 11 and 99
     */
    Challenge randomChallenge();
}
