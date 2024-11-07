package seedu.type;

import seedu.recurrence.RecurrenceFrequency;

import java.time.LocalDate;

/**
 * Represents an income entry with specific details such as amount, description, date, tag, and recurrence frequency.
 * This class extends {@link EntryType} and inherits its properties.
 */
public class Income extends EntryType {
    public Income(double amount, String description, LocalDate date, String tag,
                  RecurrenceFrequency recurrenceFrequency, LocalDate lastRecurrence, int dayOfRecurrence) {
        super(amount, description, date, tag, recurrenceFrequency, lastRecurrence, dayOfRecurrence);
    }

    public Income(Income entry) {
        super(entry);
    }

    public Income(String fullCommand, double amount, String description) {
        super(fullCommand, amount, description);
    }
}
