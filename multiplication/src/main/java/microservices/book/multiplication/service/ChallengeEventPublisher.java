package microservices.book.multiplication.service;

import microservices.book.multiplication.challenge.ChallengeAttempt;
import microservices.book.multiplication.challenge.ChallengeSolvedEvent;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ChallengeEventPublisher {

    private final AmqpTemplate amqpTemplate;
    private final String challengesTopicExchange;

    public ChallengeEventPublisher(
            AmqpTemplate amqpTemplate,
            @Value("${amqp.exchange.attempts}") final String challengesTopicExchange)
    {
        this.amqpTemplate = amqpTemplate;
        this.challengesTopicExchange = challengesTopicExchange;
    }

    public void publishChallengeSolvedEvent(final ChallengeAttempt challengeAttempt) {
        ChallengeSolvedEvent challengeSolvedEvent = buildEvent(challengeAttempt);
        // The routing key must be either "attempt.correct" or "attempt.wrong"
        String routingKey = "attempts." + (challengeAttempt.isCorrect() ? "correct" : "wrong");
        amqpTemplate.convertAndSend(challengesTopicExchange, routingKey, challengeSolvedEvent);
    }

    private ChallengeSolvedEvent buildEvent(final ChallengeAttempt attempt) {
        return new ChallengeSolvedEvent(attempt.getId(),
                attempt.isCorrect(), attempt.getFactorA(),
                attempt.getFactorB(), attempt.getUser().getId(),
                attempt.getUser().getAlias());
    }
}
