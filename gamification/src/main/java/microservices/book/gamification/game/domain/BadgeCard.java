package microservices.book.gamification.game.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

/**
 * This class links a Badge to a User. Contains also a timestamp with the moment in which the user got it.
 */
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class BadgeCard {

    @Id
    @GeneratedValue
    private Long badgeId;

    private Long userId;
    @EqualsAndHashCode.Exclude
    private long badgeTimestamp;
    private BadgeType badgeType;

    public BadgeCard(final Long userId, final BadgeType badgeType) {
        this(null, userId, System.currentTimeMillis(), badgeType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BadgeCard badgeCard = (BadgeCard) o;
        return Objects.equals(badgeId, badgeCard.badgeId);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
