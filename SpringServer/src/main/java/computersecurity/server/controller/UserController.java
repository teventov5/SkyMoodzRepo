package computersecurity.server.controller;

import computersecurity.server.model.User;
import computersecurity.server.model.UserService;
import computersecurity.server.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static computersecurity.server.config.JwtAuthenticationFilter.AUTHORIZATION_HEADER;

/**
 * All user RESTful web services are in this controller class.
 *
 * @since 21-Mar-21
 */
@RestController
@RequestMapping("/user")
public class UserController {
    static final String USER_IS_ALREADY_REGISTERED_BAD_REQUEST = "User is already signed up";
    static final String USER_DETAILS_ARE_MANDATORY_SIGN_UP_BAD_REQUEST = "Missing user details for sign up";
    static final String USER_DETAILS_ARE_MANDATORY_SIGN_IN_BAD_REQUEST = "Missing user details for sign in/out";
    static final String USER_IS_NOT_SIGNED_UP_BAD_REQUEST = "User is not signed up";
    static final String WRONG_USERNAME_PASS_BAD_REQUEST = "Incorrect user name or password";

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @CrossOrigin
    @PutMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody User user) {
        try {
            if ((user == null) || (user.getId() == null) || user.getId().isBlank() ||
                    (user.getName() == null) || user.getName().isBlank() || (user.getDateOfBirth() == null)) {
                return ResponseEntity.badRequest().body(USER_DETAILS_ARE_MANDATORY_SIGN_UP_BAD_REQUEST);
            }

            if (userService.existsById(user.getId())) {
                return ResponseEntity.badRequest().body(USER_IS_ALREADY_REGISTERED_BAD_REQUEST);
            }

            User response = new User(userService.save(user));
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body(USER_DETAILS_ARE_MANDATORY_SIGN_UP_BAD_REQUEST);
        } catch (Throwable t) {
            return ControllerErrorHandler.handleServerError(t);
        }
    }

    @CrossOrigin
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody User user) {
        try {
            if ((user == null) || (user.getId() == null) || user.getId().isBlank() ||
                    (user.getPwd() == null) || (user.getPwd().length == 0)) {
                return ResponseEntity.badRequest().body(USER_DETAILS_ARE_MANDATORY_SIGN_IN_BAD_REQUEST);
            }

            try {
                authenticationManager.authenticate(user.toAuthToken());
            } catch (UsernameNotFoundException e) {
                // We never get here since it is less secure to reveal that the provided user name is not signed up.
                return ResponseEntity.badRequest().body(USER_IS_NOT_SIGNED_UP_BAD_REQUEST);
            } catch (BadCredentialsException e) {
                return ResponseEntity.badRequest().body(WRONG_USERNAME_PASS_BAD_REQUEST);
            } catch (AuthenticationException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to sign in: " + e.getMessage());
            }

            Optional<? extends User> userEntity = userService.findById(user.getId());
            String token = jwtUtils.generateToken(userEntity.get());
            return ResponseEntity.ok("{ \"token\" : \"" + token + "\" }");
        } catch (Throwable t) {
            return ControllerErrorHandler.handleServerError(t);
        }
    }

    @PutMapping("/signout")
    public ResponseEntity<?> signOut(@RequestHeader(AUTHORIZATION_HEADER) String jwtToken) {
        try {
            if ((jwtToken == null) || jwtToken.isBlank()) {
                return ResponseEntity.badRequest().body(USER_DETAILS_ARE_MANDATORY_SIGN_IN_BAD_REQUEST);
            }

            User user = jwtUtils.parseToken(jwtToken);
            if (user == null) {
                return ResponseEntity.ok("Invalid token");
            }

            Optional<? extends User> userEntity = userService.findById(user.getId());

            // In case user does not exist in our repository, return a bad request.
            if (userEntity.isEmpty()) {
                return ResponseEntity.badRequest().body(USER_IS_NOT_SIGNED_UP_BAD_REQUEST);
            } else if (SecurityContextHolder.getContext().getAuthentication() == null) {
                return ResponseEntity.ok("User is already signed out");
            } else {
                // Remove authentication information due to sign out.
                SecurityContextHolder.getContext().setAuthentication(null);
                return ResponseEntity.ok("Good Bye");
            }
        } catch (Throwable t) {
            return ControllerErrorHandler.handleServerError(t);
        }
    }

    @GetMapping("/{userId}/info")
    public ResponseEntity<?> userInfo(@PathVariable String userId) {
        try {
            if ((userId == null) || userId.isBlank()) {
                return ResponseEntity.notFound().build();
            }

            Optional<? extends User> userEntity = userService.findById(userId);

            // In case user does not exist in our repository, return a bad request.
            if (userEntity.isEmpty()) {
                return ResponseEntity.badRequest().body(USER_IS_NOT_SIGNED_UP_BAD_REQUEST);
            } else {
                User responseUser = new User(userEntity.get());
                return ResponseEntity.ok(responseUser);
            }
        } catch (Throwable t) {
            return ControllerErrorHandler.handleServerError(t);
        }
    }
}

