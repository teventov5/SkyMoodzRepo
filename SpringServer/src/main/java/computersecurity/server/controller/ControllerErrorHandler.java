package computersecurity.server.controller;

import com.fasterxml.jackson.databind.node.TextNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * A class to be used as utility class for creating error response for any unexpected error in the controllers
 *
 * @since 21-Mar-21
 */
public class ControllerErrorHandler {
    /**
     * Use this method to return an internal server error response, or bad request, depends on the type of the error.<br/>
     * In case the specified thrown is instance of IllegalArgumentException, this indicates a client error at the service layer,
     * hence we return a BAD REQUEST for that. Otherwise, this is an unexpected server error.
     * @param t A thrown to handle
     * @return A response entity with the exception message as body. (JsonNode, and not simple string)
     */
    public static ResponseEntity<?> handleServerError(Throwable t) {
        if (t instanceof IllegalArgumentException) {
            System.err.println("Bad Request: " + t.getMessage());
            return ResponseEntity.badRequest().body(new TextNode(t.getMessage()));
        }

        System.err.println("Unexpected error has occurred: " + t);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new TextNode("Unexpected error has occurred. Reason: " + t.getMessage()));
    }
}

