package microservices.book.multiplication.challenge;

import lombok.Value;

@Value
public class ChallengeSolvedEvent {
    Long id;
    boolean correct;
    int factorA;
    int factorB;
    Long userId;
    String alias;
}
