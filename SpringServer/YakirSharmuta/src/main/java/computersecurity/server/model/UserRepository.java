package computersecurity.server.model;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Exposes CRUD operations implemented by spring.<br/>
 * Do not use a repository directly. Instead, auto wire a reference of {@link UserService}
 *
 * @since 21-Mar-21
 */
public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findByIdIgnoreCase(String id);

    boolean existsByIdIgnoreCase(String id);
}

