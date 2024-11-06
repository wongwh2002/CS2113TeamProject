package seedu.storage;

import seedu.commands.CommandUtils;
import seedu.exception.WiagiInvalidInputException;
import seedu.exception.WiagiStorageCorruptedException;
import seedu.recurrence.RecurrenceFrequency;
import seedu.type.EntryType;
import seedu.type.Income;
import seedu.type.Spending;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private static final Logger logger = Logger.getLogger(LoadStorageCheck.class.getName());
    public static final String STORAGE_LAST_RECURRED_DATE = "last recurred date!";
    public static final String STORAGE_RECURRENCE_FREQUENCY = "recurrence frequency!";
    public static final String STORAGE_DAY_RECURRENCE = "day of recurrence!";
    public static final String STORAGE_VALID_DAY_RECURRENCE = "day of recurrence between 1 and 31!";
    public String storageType;
    public final String CORRUPTED_ERROR_MESSAGE;
    public final String STORAGE_ERROR_MESSAGE;

    public LoadStorageCheck(String storageType) {
        this.storageType = storageType;
        this.CORRUPTED_ERROR_MESSAGE = "Corrupted " + storageType + " entry detected, ";
        this.STORAGE_ERROR_MESSAGE = CORRUPTED_ERROR_MESSAGE + "error with ";
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

        assert amount > 0 : "Amount should be greater than 0";
        assert description != null && !description.isEmpty() : "Description should not be null or empty";
        assert date != null : "Date should not be null";
        assert tag != null : "Tag should not be null";
        assert recurrenceFrequency != null : "Recurrence frequency should not be null";
        assert recurrenceFrequency != RecurrenceFrequency.NONE || lastRecurred != null :
                "Last recurred date should not be null if recurrence frequency is not NONE";
        assert dayOfRecurrence >= 1 && dayOfRecurrence <= 31 : "Day of recurrence should be between 1 and 31";

        if (this.storageType.equals("income")) {
            return new Income(amount, description, date, tag, recurrenceFrequency, lastRecurred, dayOfRecurrence);
        }
        return new Spending(amount, description, date, tag, recurrenceFrequency, lastRecurred, dayOfRecurrence);
    }

    private void validateEntryDataLength(String[] entryData) {
        if (entryData.length != 7) {
            logger.log(Level.WARNING, CORRUPTED_ERROR_MESSAGE + "supposed to have 7 parameters!");
            throw new WiagiStorageCorruptedException(CORRUPTED_ERROR_MESSAGE + "supposed to have 7 parameters!");
        }
        assert entryData.length == 7 : CORRUPTED_ERROR_MESSAGE + "supposed to have 7 parameters";
    }

    private double parseAmount(String amountStr) {
        try {
            double amount = CommandUtils.formatAmount(amountStr, "");
            assert amount > 0 : "Amount should be greater than 0";
            return amount;
        } catch (WiagiInvalidInputException e) {
            logger.log(Level.WARNING, STORAGE_ERROR_MESSAGE + "amount", e);
            throw new WiagiStorageCorruptedException(STORAGE_ERROR_MESSAGE + "amount");
        }
    }

    private String validateDescription(String description) {
        if (description == null || description.isEmpty()) {
            logger.log(Level.WARNING, STORAGE_ERROR_MESSAGE + "description!");
            throw new WiagiStorageCorruptedException(STORAGE_ERROR_MESSAGE + "description!");
        }
        assert description != null && !description.isEmpty() : "Description should not be null or empty";
        return description;
    }

    private LocalDate parseDate(String dateStr) {
        try {
            LocalDate date = LocalDate.parse(dateStr);
            assert date != null : "Date should not be null";
            return date;
        } catch (Exception e) {
            logger.log(Level.WARNING, STORAGE_ERROR_MESSAGE + "date!", e);
            throw new WiagiStorageCorruptedException(STORAGE_ERROR_MESSAGE + "date!");
        }
    }

    private String validateTag(String tag) {
        if (tag == null) {
            logger.log(Level.WARNING, STORAGE_ERROR_MESSAGE + "tag!");
            throw new WiagiStorageCorruptedException(STORAGE_ERROR_MESSAGE + "tag!");
        }
        assert tag != null : "Tag should not be null";
        return tag;
    }

    private RecurrenceFrequency parseRecurrenceFrequency(String recurrenceStr) {
        try {
            RecurrenceFrequency recurrenceFrequency = RecurrenceFrequency.valueOf(recurrenceStr);
            assert recurrenceFrequency != null : "Recurrence frequency should not be null";
            return recurrenceFrequency;
        } catch (IllegalArgumentException e) {
            logger.log(Level.WARNING, STORAGE_ERROR_MESSAGE + STORAGE_RECURRENCE_FREQUENCY, e);
            throw new WiagiStorageCorruptedException(STORAGE_ERROR_MESSAGE + STORAGE_RECURRENCE_FREQUENCY);
        }
    }

    private LocalDate parseLastRecurredDate(String lastRecurredStr, RecurrenceFrequency recurrenceFrequency) {
        LocalDate lastRecurred = null;
        if (!lastRecurredStr.equals(NO_RECURRENCE)) {
            lastRecurred = LocalDate.parse(lastRecurredStr);
        }

        if (recurrenceFrequency != RecurrenceFrequency.NONE && lastRecurred == null) {
            logger.log(Level.WARNING, STORAGE_ERROR_MESSAGE + STORAGE_LAST_RECURRED_DATE);
            throw new WiagiStorageCorruptedException(STORAGE_ERROR_MESSAGE + STORAGE_LAST_RECURRED_DATE);
        }
        assert recurrenceFrequency != RecurrenceFrequency.NONE || lastRecurred != null :
                "Corrupted income entry detected, error with recurred date";
        return lastRecurred;
    }

    private int parseDayOfRecurrence(String dayOfRecurrenceStr) {
        int dayOfRecurrence;
        try {
            dayOfRecurrence = Integer.parseInt(dayOfRecurrenceStr);
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, STORAGE_ERROR_MESSAGE + STORAGE_DAY_RECURRENCE, e);
            throw new WiagiStorageCorruptedException(STORAGE_ERROR_MESSAGE + STORAGE_DAY_RECURRENCE);
        }
        if (dayOfRecurrence < 1 || dayOfRecurrence > 31) {
            logger.log(Level.WARNING, STORAGE_ERROR_MESSAGE + STORAGE_VALID_DAY_RECURRENCE);
            throw new WiagiStorageCorruptedException(STORAGE_ERROR_MESSAGE + STORAGE_VALID_DAY_RECURRENCE);
        }
        assert dayOfRecurrence >= 1 && dayOfRecurrence <= 31 : "Day of recurrence should be between 1 and 31";
        return dayOfRecurrence;
    }
}