package seedu.type;

import seedu.exception.WiagiEmptyDescriptionException;
import seedu.exception.WiagiInvalidInputException;
import seedu.exception.WiagiMissingParamsException;

import java.time.LocalDate;

public class Spending extends Type {
    public Spending(String[] userInputWords, String userInput)
            throws WiagiEmptyDescriptionException, WiagiMissingParamsException {
        super(userInputWords, userInput);
    }

    public Spending(int amount, String description, LocalDate date) {
        super(amount, description, date);
    }
}
