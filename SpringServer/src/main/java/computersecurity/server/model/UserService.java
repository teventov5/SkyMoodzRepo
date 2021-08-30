package computersecurity.server.model;

import computersecurity.server.model.User;
import computersecurity.server.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Wrap the access to the repository, so we can add additional logic between controller and repository.</br>
 * For example, we use a cache for better performance, so it is done in the service, rather than repository.
 *
 * @since 21-Mar-21
 */
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * See {@link UserRepository#findById(Object)}
     */
    @CachePut(value = "userCache", key = "#id", condition = "#result != null") // Cache the results of this method because we call it from AuthorizationFilter, and we need it to be as fast as possible.
    public Optional<? extends User> findById(String id) {
        return userRepository.findByIdIgnoreCase(id);
    }

    /**
     * See {@link UserRepository#save(Object)}<br/>
     * <b>Note</b> that this method will encode user password, so you must not save a previously saved user reference,
     * to avoid of encoding an encoded password.
     * @throws BadCredentialsException When password is null or empty
     */
    @CacheEvict(value = "userCache", key = "#user.id") // When we save, we want to remove item from cache, in order to have the up to date item in the cache.
    public User save(User user) throws BadCredentialsException {
        User userForDb = new User(user);

        if ((userForDb.getPwd() == null) || (userForDb.getPwd().length == 0)) {
            throw new BadCredentialsException("Password was empty");
        }

        // Do not save passwords as clear text and achieve a higher security level this way.
        userForDb.encodePassword(passwordEncoder);
        return userRepository.save(userForDb);
    }

    /**
     * See {@link UserRepository#existsById(Object)}
     */
    public boolean existsById(String id) {
        return userRepository.existsByIdIgnoreCase(id);
    }

    /**
     * See {@link UserRepository#deleteAll()}
     */
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<? extends User> user = findById(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User " + username + " is not registered. Please sign up");
        }

        return ((User) user.get()).toUserDetails();
    }
}

