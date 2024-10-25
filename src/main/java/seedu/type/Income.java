package seedu.type;

import seedu.exception.WiagiEmptyDescriptionException;
import seedu.exception.WiagiInvalidInputException;
import seedu.exception.WiagiMissingParamsException;
import seedu.recurrence.RecurrenceFrequency;

import java.time.LocalDate;

public class Income extends Type {
    public Income(int amount, String description, LocalDate date, String tag, RecurrenceFrequency recurrenceFrequency,
                  LocalDate lastRecurrence, int dayOfRecurrence) {
        super(amount, description, date, tag, recurrenceFrequency, lastRecurrence, dayOfRecurrence);
    }

    public Income(Income entry) {
        super(entry);
    }

    public Income(String fullCommand, int amount, String description) {
        super(fullCommand, amount, description);
    }
}
