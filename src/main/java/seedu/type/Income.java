package seedu.type;

import seedu.exception.WiagiEmptyDescriptionException;
import seedu.exception.WiagiInvalidInputException;
import seedu.exception.WiagiMissingParamsException;
import seedu.recurrence.RecurrenceFrequency;

import java.time.LocalDate;

public class Income extends Type {
    public Income(String[] userInputWords, String userInput)
            throws WiagiEmptyDescriptionException, WiagiMissingParamsException, WiagiInvalidInputException {
        super(userInputWords, userInput);
    }

    public Income(int amount, String description, LocalDate date, String tag, RecurrenceFrequency recurrenceFrequency,
                  LocalDate lastRecurrence) {
        super(amount, description, date, tag, recurrenceFrequency, lastRecurrence);
    }

    public Income(Income entry) {
        super(entry);
    }
}
