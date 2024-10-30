package seedu.exception;

/**
 * Signals that the user has not inputted all the compulsory arguments needed for the command that the user is trying
 * to use
 */
public class WiagiMissingParamsException extends Exception {
    public WiagiMissingParamsException(String message) {
        super(message);
    }
}
