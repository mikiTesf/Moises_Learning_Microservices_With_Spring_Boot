package microservices.book.multiplication.challenge;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Value
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class ChallengeAttemptDTO {
    @Min(1)
    @Max(99)
    int factorA, factorB;

    @NotBlank
    String userAlias;

    @Positive
    int guess;
}
