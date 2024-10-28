package seedu.type;

import seedu.recurrence.RecurrenceFrequency;

import java.time.LocalDate;

public class Spending extends Type {
    public Spending(double amount, String description, LocalDate date, String tag,
                    RecurrenceFrequency recurrenceFrequency, LocalDate lastRecurrence, int dayOfRecurrence) {
        super(amount, description, date, tag, recurrenceFrequency, lastRecurrence, dayOfRecurrence);
    }

    public Spending(Spending other) {
        super(other);
    }

    public Spending(String fullCommand, double amount, String description) {
        super(fullCommand, amount, description);
    }
}
