package microservices.book.multiplication.service;

import microservices.book.multiplication.challenge.ChallengeAttempt;
import microservices.book.multiplication.challenge.ChallengeAttemptDTO;

public interface ChallengeService {

    ChallengeAttempt verifyAttempt(ChallengeAttemptDTO challengeAttemptDTO);
}
