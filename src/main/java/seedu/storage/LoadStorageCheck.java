package seedu.storage;

import seedu.commands.CommandUtils;
import seedu.exception.WiagiInvalidInputException;
import seedu.exception.WiagiStorageCorruptedException;
import seedu.recurrence.RecurrenceFrequency;

import java.time.LocalDate;

import static seedu.classes.Constants.NO_RECURRENCE;

public class LoadStorageCheck {
    public String storageType;
    public final String CORRUPTED_ERROR_MESSAGE = "Corrupted " + storageType + " entry detected, ";
    public final String STORAGE_ERROR_MESSAGE = CORRUPTED_ERROR_MESSAGE + "error with ";

    public LoadStorageCheck(String storageType) {
        this.storageType = storageType;
    }

    public void validateEntryDataLength(String[] entryData) {
        if (entryData.length != 7) {
            throw new WiagiStorageCorruptedException(CORRUPTED_ERROR_MESSAGE + "supposed to have 7 parameters!");
        }
        assert entryData.length == 7 : CORRUPTED_ERROR_MESSAGE + "supposed to have 7 entries";
    }

    public double parseAmount(String amountStr) {
        try {
            return CommandUtils.formatAmount(amountStr, "");
        } catch (WiagiInvalidInputException e) {
            throw new WiagiStorageCorruptedException(STORAGE_ERROR_MESSAGE + "amount");
        }
    }

    public static String validateDescription(String description) {
        if (description == null || description.isEmpty()) {
            throw new WiagiStorageCorruptedException(STORAGE_ERROR_MESSAGE + "description!");
        }
        return description;
    }

    public static LocalDate parseDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr);
        } catch (Exception e) {
            throw new WiagiStorageCorruptedException(STORAGE_ERROR_MESSAGE + "date!");
        }
    }

    public static String validateTag(String tag) {
        if (tag == null) {
            throw new WiagiStorageCorruptedException(STORAGE_ERROR_MESSAGE + "tag!");
        }
        return tag;
    }

    public static RecurrenceFrequency parseRecurrenceFrequency(String recurrenceStr) {
        try {
            return RecurrenceFrequency.valueOf(recurrenceStr);
        } catch (IllegalArgumentException e) {
            throw new WiagiStorageCorruptedException(STORAGE_ERROR_MESSAGE + "recurrence frequency!");
        }
    }

    public static LocalDate parseLastRecurredDate(String lastRecurredStr, RecurrenceFrequency recurrenceFrequency) {
        LocalDate lastRecurred = null;
        if (!lastRecurredStr.equals(NO_RECURRENCE)) {
            lastRecurred = LocalDate.parse(lastRecurredStr);
        }

        if (recurrenceFrequency != RecurrenceFrequency.NONE && lastRecurred == null) {
            throw new WiagiStorageCorruptedException(STORAGE_ERROR_MESSAGE + "last recurred date!");
        }
        assert recurrenceFrequency != RecurrenceFrequency.NONE || lastRecurred != null : STORAGE_ERROR_MESSAGE + "recurred date";
        return lastRecurred;
    }

    public static int parseDayOfRecurrence(String dayOfRecurrenceStr) {
        int dayOfRecurrence;
        try {
            dayOfRecurrence = Integer.parseInt(dayOfRecurrenceStr);
        } catch (NumberFormatException e) {
            throw new WiagiStorageCorruptedException(STORAGE_ERROR_MESSAGE + "day of recurrence!");
        }
        if (dayOfRecurrence < 1 || dayOfRecurrence > 31) {
            throw new WiagiStorageCorruptedException(STORAGE_ERROR_MESSAGE + "day of recurrence between 1 and 31!");
        }
        return dayOfRecurrence;
    }
}