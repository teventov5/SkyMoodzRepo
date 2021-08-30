package computersecurity.server.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import computersecurity.server.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

/**
 * JWT utilities is used to generate JWT token from user, and parse a JWT token into user.
 *
 * @since 21-Mar-21
 */
@Component
public class JwtUtils {
    private static final String USER_NAME = "userName";

    @Value("${server.ssl.key-store}")
    private String keyStoreFile;

    @Value("${server.ssl.key-store-password}")
    private String keyStorePass;

    @Value("${server.ssl.key-alias}")
    private String keyStoreAlias;

    /**
     * A secret key to use for signing the JWT.<br/>
     * We generate a new one every time the server starts up.
     */
    private volatile Key key;

    /**
     * Tries to parse the specified String as a JWT token.<br/>
     * If successful, return User object with user identifier and user name (extracted from token).<br/>
     * If unsuccessful (token is invalid or not containing all required user properties), simply return null.
     *
     * @param jwtToken The JWT token to parse
     * @return The User object extracted from specified token or null if the token is invalid.
     */
    public User parseToken(String jwtToken) {
        User user = null;
        if ((jwtToken != null) && !jwtToken.isBlank()) {
            initKeyIfNeeded();

            try {
                //@formatter:off
                 Claims body = Jwts.parserBuilder()
                                   .setSigningKey(key)
                                   .build()
                                   .parseClaimsJws(jwtToken.replace("Bearer ", ""))
                                   .getBody();
                 //@formatter:on

                user = new User(body.getSubject(), String.valueOf(body.get(USER_NAME)));
            } catch (Exception e) {
                System.err.println("Error has occurred while parsing JWT token: " + e);
            }
        }

        return user;
    }

    /**
     * Generates a JWT token containing userId as subject, and user name as additional claim.<br/>
     * Tokens validity is infinite.
     *
     * @param user The user for which the token will be generated
     * @return The JWT token
     */
    public String generateToken(User user) {
        initKeyIfNeeded();
        Claims claims = Jwts.claims().setSubject(user.getId());
        claims.put(USER_NAME, user.getName());
        return Jwts.builder().setClaims(claims).signWith(key).compact();
    }

    private void initKeyIfNeeded() {
        if (key == null) {
            synchronized (this) {
                if (key == null) {
                    try {
                        key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
                        System.out.println("Key has been loaded and ready for use by JwtUtils");
                    } catch (Exception e) {
                        System.err.println("Error has occurred while loading key store: " + e);
                    }
                }
            }
        }
    }
}

