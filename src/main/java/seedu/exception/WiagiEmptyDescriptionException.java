package seedu.exception;

/**
 * Signals that the user added an entry that lacks description
 */
public class WiagiEmptyDescriptionException extends Exception {
    public WiagiEmptyDescriptionException(String message) {
        super(message);
    }
}
