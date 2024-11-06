package seedu.storage;

import seedu.commands.CommandUtils;
import seedu.exception.WiagiInvalidInputException;
import seedu.exception.WiagiStorageCorruptedException;
import seedu.recurrence.RecurrenceFrequency;
import seedu.type.EntryType;
import seedu.type.Income;
import seedu.type.Spending;

import java.time.LocalDate;
import java.util.Map;

import static seedu.classes.Constants.LOAD_AMOUNT_INDEX;
import static seedu.classes.Constants.LOAD_DATE_INDEX;
import static seedu.classes.Constants.LOAD_DAY_OF_RECURRENCE_INDEX;
import static seedu.classes.Constants.LOAD_DESCRIPTION_INDEX;
import static seedu.classes.Constants.LOAD_LAST_RECURRED_INDEX;
import static seedu.classes.Constants.LOAD_RECURRENCE_INDEX;
import static seedu.classes.Constants.LOAD_TAG_INDEX;
import static seedu.classes.Constants.NO_RECURRENCE;
import static seedu.classes.Constants.STORAGE_LOAD_SEPARATOR;

public class LoadStorageCheck {
    public String storageType;
    public final String CORRUPTED_ERROR_MESSAGE = "Corrupted " + storageType + " entry detected, ";
    public final String STORAGE_ERROR_MESSAGE = CORRUPTED_ERROR_MESSAGE + "error with ";

    public LoadStorageCheck(String storageType) {
        this.storageType = storageType;
    }

    public EntryType parseEntry(String newEntry) {
        String[] entryData = newEntry.split(STORAGE_LOAD_SEPARATOR);
        validateEntryDataLength(entryData);

        double amount = parseAmount(entryData[LOAD_AMOUNT_INDEX]);
        String description = validateDescription(entryData[LOAD_DESCRIPTION_INDEX]);
        LocalDate date = parseDate(entryData[LOAD_DATE_INDEX]);
        String tag = validateTag(entryData[LOAD_TAG_INDEX]);
        RecurrenceFrequency recurrenceFrequency = parseRecurrenceFrequency(entryData[LOAD_RECURRENCE_INDEX]);
        LocalDate lastRecurred = parseLastRecurredDate(entryData[LOAD_LAST_RECURRED_INDEX], recurrenceFrequency);
        int dayOfRecurrence = parseDayOfRecurrence(entryData[LOAD_DAY_OF_RECURRENCE_INDEX]);
        if (this.storageType.equals("income")) {
            return new Income(amount, description, date, tag, recurrenceFrequency, lastRecurred, dayOfRecurrence);
        }
        return new Spending(amount, description, date, tag, recurrenceFrequency, lastRecurred, dayOfRecurrence);
    }

    private static void validateEntryDataLength(String[] entryData) {
        if (entryData.length != 7) {
            throw new WiagiStorageCorruptedException("Corrupted income entry detected, supposed to have 7 parameters!");
        }
        assert entryData.length == 7 : "Corrupted income entry detected, supposed to have 7 entries";
    }

    private static double parseAmount(String amountStr) {
        try {
            return CommandUtils.formatAmount(amountStr, "");
        } catch (WiagiInvalidInputException e) {
            throw new WiagiStorageCorruptedException("Corrupted income entry detected, error with amount");
        }
    }

    private static String validateDescription(String description) {
        if (description == null || description.isEmpty()) {
            throw new WiagiStorageCorruptedException("Corrupted income entry detected, error with description!");
        }
        return description;
    }

    private static LocalDate parseDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr);
        } catch (Exception e) {
            throw new WiagiStorageCorruptedException("Corrupted income entry detected, error with date!");
        }
    }

    private static String validateTag(String tag) {
        if (tag == null) {
            throw new WiagiStorageCorruptedException("Corrupted income entry detected, error with tag!");
        }
        return tag;
    }

    private static RecurrenceFrequency parseRecurrenceFrequency(String recurrenceStr) {
        try {
            return RecurrenceFrequency.valueOf(recurrenceStr);
        } catch (IllegalArgumentException e) {
            throw new WiagiStorageCorruptedException("Corrupted income entry detected, error with recurrence frequency!");
        }
    }

    private static LocalDate parseLastRecurredDate(String lastRecurredStr, RecurrenceFrequency recurrenceFrequency) {
        LocalDate lastRecurred = null;
        if (!lastRecurredStr.equals(NO_RECURRENCE)) {
            lastRecurred = LocalDate.parse(lastRecurredStr);
        }

        if (recurrenceFrequency != RecurrenceFrequency.NONE && lastRecurred == null) {
            throw new WiagiStorageCorruptedException("Corrupted income entry detected, error with last recurred date!");
        }
        assert recurrenceFrequency != RecurrenceFrequency.NONE || lastRecurred != null : "Corrupted income entry detected, error with recurred date";
        return lastRecurred;
    }

    private static int parseDayOfRecurrence(String dayOfRecurrenceStr) {
        int dayOfRecurrence;
        try {
            dayOfRecurrence = Integer.parseInt(dayOfRecurrenceStr);
        } catch (NumberFormatException e) {
            throw new WiagiStorageCorruptedException("Corrupted income entry detected, error with day of recurrence!");
        }
        if (dayOfRecurrence < 1 || dayOfRecurrence > 31) {
            throw new WiagiStorageCorruptedException("Corrupted income entry detected, error with day of recurrence between 1 and 31!");
        }
        return dayOfRecurrence;
    }
}
