package microservices.book.multiplication.challenge;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import microservices.book.multiplication.user.User;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

/**
 * Identifies the attempt from a {@link User} to solve a challenge
 */
@Entity
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ChallengeAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    @ToString.Exclude
    private User user;

    private int factorA;

    private int factorB;

    private int resultAttempt;

    private boolean correct;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ChallengeAttempt attempt = (ChallengeAttempt) o;
        return Objects.equals(id, attempt.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
