package seedu.type;

import seedu.recurrence.RecurrenceFrequency;

import java.time.LocalDate;

public class Spending extends Type {
    public Spending(int amount, String description, LocalDate date, String tag,
                    RecurrenceFrequency recurrenceFrequency, LocalDate lastRecurrence, int dayOfRecurence) {
        super(amount, description, date, tag, recurrenceFrequency, lastRecurrence, dayOfRecurence);
    }

    public Spending(Spending other) {
        super(other);
    }

    public Spending(String fullCommand, double amount, String description) {
        super(fullCommand, amount, description);
    }
}
