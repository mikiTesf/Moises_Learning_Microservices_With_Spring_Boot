package microservices.book.gamification.game;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservices.book.gamification.challenge.ChallengeSolvedEvent;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class GameEventHandler {

    private final GameService gameService;

    @RabbitListener(queues = "${amqp.queue.gamification}")
    void handleMultiplicationSolved(ChallengeSolvedEvent challengeSolvedEvent) {
        log.info("Challenge Solved Event received: {}", challengeSolvedEvent.getAttemptId());

        try {
            gameService.newAttemptForUser(challengeSolvedEvent);
        } catch (final Exception e) {
            log.error("Error when trying to process ChallengeSolvedEvent", e);
            // Avoids re-queuing and processing the event
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }
}
