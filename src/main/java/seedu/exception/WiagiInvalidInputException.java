package seedu.exception;

/**
 * Signals that the user has given an invalid input for an entry
 */
public class WiagiInvalidInputException extends RuntimeException {
    public WiagiInvalidInputException(String message) {
        super(message);
    }
}
