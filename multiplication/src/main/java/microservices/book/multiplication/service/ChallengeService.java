package microservices.book.multiplication.service;

import microservices.book.multiplication.challenge.ChallengeAttempt;
import microservices.book.multiplication.challenge.ChallengeAttemptDTO;

import java.util.List;

public interface ChallengeService {

    ChallengeAttempt verifyAttempt(ChallengeAttemptDTO challengeAttemptDTO);

    List<ChallengeAttempt> getLast10Attempts(String userAlias);
}
