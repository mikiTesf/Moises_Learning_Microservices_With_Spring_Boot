package microservices.book.multiplication.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * @param alias The name passed along with the attempt
     * @return An {@link Optional} wrapping a {@link User} object
     */
    Optional<User> findByAlias(final String alias);

    /**
     * @param idList A list of user IDs
     * @return A list of {@link User} object(s)
     */
    List<User> findAllByIdIn(List<Long> idList);
}
