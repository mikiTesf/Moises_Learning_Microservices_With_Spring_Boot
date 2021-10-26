package microservices.book.gamification.game;

import microservices.book.gamification.challenge.ChallengeSolvedEvent;
import microservices.book.gamification.game.GameService.GameResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameEventHandlerTest {

    @Mock
    private GameService gameService;

    private GameEventHandler gameEventHandler;

    @BeforeEach
    void setUp() {
        GameResult expectedGameResult = new GameResult(100, List.of());
        gameEventHandler = new GameEventHandler(gameService);
        // Mock setup
        when(gameService.newAttemptForUser(any(ChallengeSolvedEvent.class))).thenReturn(expectedGameResult);
    }

    @Test
    void handleMultiplicationSolved() {
        // given
        ChallengeSolvedEvent challengeSolvedEvent = new ChallengeSolvedEvent(
                1L, true, 1, 1, 1L, "john_doe"
        );
        // when
        gameEventHandler.handleMultiplicationSolved(challengeSolvedEvent);
        // then
        verify(gameService).newAttemptForUser(challengeSolvedEvent);
    }
}
