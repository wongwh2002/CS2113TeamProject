package seedu.exception;

public class WiagiEmptyDescriptionException extends Exception {
    public WiagiEmptyDescriptionException() {
        super("No Description Input!");
    } public WiagiEmptyDescriptionException(String message) {
        super(message);
    }
}
