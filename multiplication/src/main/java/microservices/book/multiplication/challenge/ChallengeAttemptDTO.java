package microservices.book.multiplication.challenge;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeAttemptDTO {
    @Min(1)
    @Max(99)
    int factorA, factorB;

    @NotBlank
    String userAlias;

    @Positive
    int guess;
}
