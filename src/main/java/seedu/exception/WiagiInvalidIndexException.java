package seedu.exception;

/**
 * Signals that the user gave an index that is invalid or inaccessible
 */
public class WiagiInvalidIndexException extends Exception {
    public WiagiInvalidIndexException(String message) {
        super(message);
    }
}
