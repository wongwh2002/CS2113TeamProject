package seedu.type;

import seedu.exception.WiagiEmptyDescriptionException;
import seedu.exception.WiagiInvalidInputException;

public class Income extends Type {
    public Income(String[] userInputWords, String userInput)
            throws WiagiEmptyDescriptionException, WiagiInvalidInputException {
        super(userInputWords, userInput);
    }

    public Income(int amount, String description) {
        super(amount, description);
    }
}
