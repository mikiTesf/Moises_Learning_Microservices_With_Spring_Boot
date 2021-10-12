package microservices.book.multiplication.challenge;

import lombok.Value;

@Value
public class ChallengeSolvedDTO {
    Long id;
    boolean correct;
    int factorA;
    int factorB;
    Long id1;
    String alias;
}
