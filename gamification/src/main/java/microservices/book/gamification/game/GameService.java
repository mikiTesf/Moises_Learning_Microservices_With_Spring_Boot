package microservices.book.gamification.game;

import lombok.Value;
import microservices.book.gamification.challenge.ChallengeSolvedEvent;
import microservices.book.gamification.game.domain.BadgeType;

import java.util.List;

/**
 * This service includes the main logic for gamifying the system.
 */
public interface GameService {

    /**
     * Process a new attempt from a given user.
     *
     * @param challengeSolvedEvent the challenge data with user details, factors, etc.
     * @return a {@link GameResult} object containing the new score and badge cards obtained
     */
    GameResult newAttemptForUser(ChallengeSolvedEvent challengeSolvedEvent);

    @Value
    class GameResult {
        int score;
        List<BadgeType> badges;
    }
}
