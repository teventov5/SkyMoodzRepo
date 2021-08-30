package computersecurity.server.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

/**
 * A user model that we persist to database.<br/>
 * In order to avoid of returning user db model sensitive information from the server, we exclude pwd and key from json.
 *
 * @since 21-Mar-21
 */
@Entity(name = "user")
public class User {
    /**
     * See {@link User#getId()}
     */
    @Id
    private String id;

    /**
     * The password of a user. We use it when signing in/up a user.
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private char[] pwd;

    /**
     * See {@link User#getName()}
     */
    private String name;

    /**
     * See {@link User#getDateOfBirth()}
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonDeserialize(using = LocalDateDeserializer.class) // Date format is: yyyy-MM-dd. e.g. 1995-08-30
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateOfBirth;

    public User() {
    }

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(String id, char[] pwd, String name, LocalDate dateOfBirth) {
        this.id = id;
        this.pwd = pwd;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Constructs a new {@link User}, copying all fields out of another user.<br/>
     * We use a copy constructor in order to convert client model to server model and vice versa
     *
     * @param user The user to get fields from
     */
    public User(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.dateOfBirth = user.getDateOfBirth();
        this.pwd = user.getPwd();
    }

    /**
     * Build a token out of this user
     *
     * @return A token to be used by authentication filter with JWT
     * @throws BadCredentialsException When password is null or empty
     */
    public UsernamePasswordAuthenticationToken toAuthToken() throws BadCredentialsException {
        if ((pwd == null) || (pwd.length == 0)) {
            throw new BadCredentialsException("Password was empty");
        }

        return new UsernamePasswordAuthenticationToken(id, new String(pwd), new ArrayList<>());
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        if (pwd != null) {
            pwd = passwordEncoder.encode(new String(pwd)).toCharArray();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public char[] getPwd() {
        return pwd;
    }

    public void setPwd(char[] pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User userDB = (User) o;
        return Objects.equals(id, userDB.id) && Arrays.equals(pwd, userDB.pwd) && Objects.equals(name, userDB.name) && Objects.equals(dateOfBirth, userDB.dateOfBirth);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, dateOfBirth);
        result = 31 * result + Arrays.hashCode(pwd);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }

    /**
     * Converts this user into {@link UserDetails} reference to be used for authenticating this user
     *
     * @return A user details reference to use for authentication
     */
    public UserDetails toUserDetails() {
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return new ArrayList<>();
            }

            @Override
            public String getPassword() {
                return new String(pwd);
            }

            @Override
            public String getUsername() {
                return id;
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }
}

