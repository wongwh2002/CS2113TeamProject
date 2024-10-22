package seedu.type;

import seedu.exception.WiagiEmptyDescriptionException;
import seedu.exception.WiagiInvalidInputException;
import seedu.exception.WiagiMissingParamsException;

import java.time.LocalDate;

public class Income extends Type {
    public Income(String[] userInputWords, String userInput)
            throws WiagiEmptyDescriptionException, WiagiMissingParamsException, WiagiInvalidInputException {
        super(userInputWords, userInput);
    }

    public Income(int amount, String description, LocalDate date) {
        super(amount, description, date);
    }
    public Income(int amount, String description, LocalDate date, String tag) {
        super(amount, description, date, tag);
    }
}
