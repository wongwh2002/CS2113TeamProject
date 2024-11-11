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
import static seedu.commands.CommandUtils.checkDateLimit;

public class LoadStorageCheck {
    public static final String STORAGE_LAST_RECURRED_DATE = "last recurred date!";
    public static final String STORAGE_RECURRENCE_FREQUENCY = "recurrence frequency!";
    public static final String STORAGE_DAY_RECURRENCE = "day of recurrence!";
    public static final String STORAGE_VALID_DAY_RECURRENCE = "day of recurrence between 1 and 31!";
    public static final String STORAGE_COMPULSORY_SIZE = "supposed to have 7 parameters!";
    public String storageType = "";
    public String corruptedErrorMessage;
    public String storageErrorMessage;
    private final Logger logger = Logger.getLogger(LoadStorageCheck.class.getName());

    public LoadStorageCheck(String storageType) {
        this.storageType = storageType;
        this.corruptedErrorMessage = "Corrupted " + storageType + " entry detected, ";
        this.storageErrorMessage = corruptedErrorMessage + "error with ";
    }

    public EntryType parseEntry(String newEntry) {
        String[] entryData = newEntry.split(STORAGE_LOAD_SEPARATOR);
        validateEntryDataLength(entryData);

        double amount = getAmount(entryData[LOAD_AMOUNT_INDEX]);
        String description = getDescription(entryData[LOAD_DESCRIPTION_INDEX]);
        LocalDate date = getDate(entryData[LOAD_DATE_INDEX]);
        String tag = getTag(entryData[LOAD_TAG_INDEX]);
        RecurrenceFrequency recurrenceFrequency = getRecurrenceFrequency(entryData[LOAD_RECURRENCE_INDEX]);
        LocalDate lastRecurred = getLastRecurredDate(entryData[LOAD_LAST_RECURRED_INDEX], recurrenceFrequency);
        int dayOfRecurrence = getDayOfRecurrence(entryData[LOAD_DAY_OF_RECURRENCE_INDEX]);

        if (this.storageType.equals("income")) {
            return new Income(amount, description, date, tag, recurrenceFrequency, lastRecurred, dayOfRecurrence);
        }
        return new Spending(amount, description, date, tag, recurrenceFrequency, lastRecurred, dayOfRecurrence);
    }

    private void validateEntryDataLength(String[] entryData) {
        if (entryData.length != 7) {
            logger.log(Level.WARNING, corruptedErrorMessage + STORAGE_COMPULSORY_SIZE);
            throw new WiagiStorageCorruptedException(corruptedErrorMessage + STORAGE_COMPULSORY_SIZE);
        }
        assert entryData.length == 7 : corruptedErrorMessage + "supposed to have 7 parameters";
    }

    private double getAmount(String amountStr) {
        try {
            double amount = CommandUtils.formatAmount(amountStr, "");
            assert amount > 0 : "Amount should be greater than 0";
            return amount;
        } catch (WiagiInvalidInputException e) {
            logger.log(Level.WARNING, storageErrorMessage + "amount", e);
            throw new WiagiStorageCorruptedException(storageErrorMessage + "amount");
        }
    }

    private String getDescription(String description) {
        if (description == null || description.isEmpty()) {
            logger.log(Level.WARNING, storageErrorMessage + "description!");
            throw new WiagiStorageCorruptedException(storageErrorMessage + "description!");
        }
        assert description != null && !description.isEmpty() : "Description should not be null or empty";
        return description;
    }

    private LocalDate getDate(String dateStr) {
        try {
            LocalDate date = LocalDate.parse(dateStr);
            checkDateLimit(date);
            assert date != null : "Date should not be null";
            return date;
        } catch (Exception e) {
            logger.log(Level.WARNING, storageErrorMessage + "date!", e);
            throw new WiagiStorageCorruptedException(storageErrorMessage + "date!");
        }
    }

    private String getTag(String tag) {
        if (tag == null) {
            logger.log(Level.WARNING, storageErrorMessage + "tag!");
            throw new WiagiStorageCorruptedException(storageErrorMessage + "tag!");
        }
        assert tag != null : "Tag should not be null";
        return tag;
    }

    private RecurrenceFrequency getRecurrenceFrequency(String recurrenceStr) {
        try {
            RecurrenceFrequency recurrenceFrequency = RecurrenceFrequency.valueOf(recurrenceStr);
            assert recurrenceFrequency != null : "Recurrence frequency should not be null";
            return recurrenceFrequency;
        } catch (IllegalArgumentException e) {
            logger.log(Level.WARNING, storageErrorMessage + STORAGE_RECURRENCE_FREQUENCY, e);
            throw new WiagiStorageCorruptedException(storageErrorMessage + STORAGE_RECURRENCE_FREQUENCY);
        }
    }

    private LocalDate getLastRecurredDate(String lastRecurredStr, RecurrenceFrequency recurrenceFrequency) {
        LocalDate lastRecurred = null;
        if (!lastRecurredStr.equals(NO_RECURRENCE)) {
            lastRecurred = LocalDate.parse(lastRecurredStr);
        }

        if (recurrenceFrequency != RecurrenceFrequency.NONE && lastRecurred == null) {
            logger.log(Level.WARNING, storageErrorMessage + STORAGE_LAST_RECURRED_DATE);
            throw new WiagiStorageCorruptedException(storageErrorMessage + STORAGE_LAST_RECURRED_DATE);
        }
        assert recurrenceFrequency == RecurrenceFrequency.NONE || lastRecurred != null :
                "Corrupted entry detected, error with recurred date";
        return lastRecurred;
    }

    private int getDayOfRecurrence(String dayOfRecurrenceStr) {
        int dayOfRecurrence;
        try {
            dayOfRecurrence = Integer.parseInt(dayOfRecurrenceStr);
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, storageErrorMessage + STORAGE_DAY_RECURRENCE, e);
            throw new WiagiStorageCorruptedException(storageErrorMessage + STORAGE_DAY_RECURRENCE);
        }
        if (dayOfRecurrence < 1 || dayOfRecurrence > 31) {
            logger.log(Level.WARNING, storageErrorMessage + STORAGE_VALID_DAY_RECURRENCE);
            throw new WiagiStorageCorruptedException(storageErrorMessage + STORAGE_VALID_DAY_RECURRENCE);
        }
        assert dayOfRecurrence >= 1 && dayOfRecurrence <= 31 : "Day of recurrence should be between 1 and 31";
        return dayOfRecurrence;
    }
}
