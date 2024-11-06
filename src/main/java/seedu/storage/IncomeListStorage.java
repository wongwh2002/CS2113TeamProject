package seedu.storage;

import seedu.classes.WiagiLogger;
import seedu.commands.CommandUtils;
import seedu.exception.WiagiInvalidInputException;
import seedu.recurrence.RecurrenceFrequency;
import seedu.type.Income;
import seedu.type.IncomeList;
import seedu.classes.Ui;
import seedu.exception.WiagiStorageCorruptedException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;

import static seedu.classes.Constants.LOAD_AMOUNT_INDEX;
import static seedu.classes.Constants.LOAD_DAY_OF_RECURRENCE_INDEX;
import static seedu.classes.Constants.LOAD_DESCRIPTION_INDEX;
import static seedu.classes.Constants.LOAD_RECURRENCE_INDEX;
import static seedu.classes.Constants.LOAD_TAG_INDEX;
import static seedu.classes.Constants.LOAD_DATE_INDEX;
import static seedu.classes.Constants.LOAD_LAST_RECURRED_INDEX;
import static seedu.classes.Constants.NO_RECURRENCE;
import static seedu.classes.Constants.SAVE_INCOME_FILE_ERROR;
import static seedu.classes.Constants.LOAD_INCOME_FILE_ERROR;
import static seedu.classes.Constants.STORAGE_SEPARATOR;
import static seedu.classes.Constants.STORAGE_LOAD_SEPARATOR;

/**
 * Manages saving and loading of income data to and from a file.
 */
public class IncomeListStorage {
    static final String INCOMES_FILE_PATH = "./incomes.txt";

    /**
     * Saves the income list, including each income entry, to a file.
     *
     * @param incomes the IncomeList to be saved.
     */
    static void save(IncomeList incomes) {
        try {
            WiagiLogger.logger.log(Level.INFO, "Starting to save incomes...");
            FileWriter fw = new FileWriter(INCOMES_FILE_PATH);
            for (Income income : incomes) {
                String incomeEntry = income.getAmount() + STORAGE_SEPARATOR + income.getDescription() +
                        STORAGE_SEPARATOR + income.getDate() + STORAGE_SEPARATOR + income.getTag() + STORAGE_SEPARATOR +
                        income.getRecurrenceFrequency() + STORAGE_SEPARATOR + income.getLastRecurrence() +
                        STORAGE_SEPARATOR + income.getDayOfRecurrence();
                fw.write(incomeEntry + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            WiagiLogger.logger.log(Level.WARNING, "Unable to save incomes file", e);
            Ui.printWithTab(SAVE_INCOME_FILE_ERROR);
        }
        WiagiLogger.logger.log(Level.INFO, "Finish saving incomes file");
    }

    /**
     * Loads the income data from a file into the application's income list.
     * If no file exists, a new one is created.
     */
    static void load() {
        WiagiLogger.logger.log(Level.INFO, "Starting to load incomes...");
        long counter = 0;
        try {
            if (new File(INCOMES_FILE_PATH).createNewFile()) {
                return;
            }
            File incomeFile = new File(INCOMES_FILE_PATH);
            Scanner incomeReader = new Scanner(incomeFile);
            while (incomeReader.hasNext()) {
                String newEntry = incomeReader.nextLine();
                counter++;
                try {
                    addLoadingEntry(newEntry);
                } catch (WiagiStorageCorruptedException e) {
                    WiagiLogger.logger.log(Level.WARNING, "Corrupted income entry detected at line "
                            + counter, e);
                    Ui.printWithTab(e.getMessage());
                    Ui.printWithTab("Detected at line " + counter + " in the incomes file.");
                    Ui.printWithTab("Deleting corrupted entry...");
                }
            }

        } catch (IOException e) {
            WiagiLogger.logger.log(Level.WARNING, "Unable to open incomes file", e);
            Ui.printWithTab(LOAD_INCOME_FILE_ERROR);
        } catch (NoSuchElementException e) {
            WiagiLogger.logger.log(Level.WARNING, "Incomes file is empty", e);
            File incomeFile = new File(INCOMES_FILE_PATH);
            incomeFile.delete();

            WiagiLogger.logger.log(Level.INFO, "Finish loading incomes file.");
        }
    }

    private static void addLoadingEntry1(String newEntry) {
        String[] entryData = newEntry.split(STORAGE_LOAD_SEPARATOR);
        LocalDate date = LocalDate.parse(entryData[LOAD_DATE_INDEX]);
        LocalDate lastRecurred = null;
        if (!entryData[LOAD_LAST_RECURRED_INDEX].equals(NO_RECURRENCE)) {
            lastRecurred = LocalDate.parse(entryData[LOAD_LAST_RECURRED_INDEX]);
        }
        Income nextEntry = new Income(Double.parseDouble(entryData[LOAD_AMOUNT_INDEX]),
                entryData[LOAD_DESCRIPTION_INDEX], date, entryData[LOAD_TAG_INDEX],
                RecurrenceFrequency.valueOf(entryData[LOAD_RECURRENCE_INDEX]),
                lastRecurred, Integer.parseInt(entryData[LOAD_DAY_OF_RECURRENCE_INDEX]));
        Storage.incomes.add(nextEntry);
    }

    private static void addLoadingEntry(String newEntry) {
        String[] entryData = newEntry.split(STORAGE_LOAD_SEPARATOR);
        if (entryData.length != 7) {
            throw new WiagiStorageCorruptedException("Corrupted income entry detected, supposed to have 7 parameters!");
        }
        assert entryData.length == 7 : "Corrupted income entry detected, supposed to have 7 entries";

        double amount;
        try {
            amount = CommandUtils.formatAmount(entryData[LOAD_AMOUNT_INDEX], "" );
        } catch (WiagiInvalidInputException e) {
            throw new WiagiStorageCorruptedException("Corrupted income entry detected, error with amount");
        }
        String description = entryData[LOAD_DESCRIPTION_INDEX];
        if (description == null || description.isEmpty()) {
            throw new WiagiStorageCorruptedException("Corrupted income entry detected, error with description!");
        }
        LocalDate date;
        try {
            date = LocalDate.parse(entryData[LOAD_DATE_INDEX]);
        } catch (Exception e) {
            throw new WiagiStorageCorruptedException("Corrupted income entry detected, error with date!");
        }
        String tag = entryData[LOAD_TAG_INDEX];
        if (tag == null) {
            throw new WiagiStorageCorruptedException("Corrupted income entry detected, error with tag!");
        }
        RecurrenceFrequency recurrenceFrequency;
        try {
            recurrenceFrequency = RecurrenceFrequency.valueOf(entryData[LOAD_RECURRENCE_INDEX]);
        } catch (IllegalArgumentException e) {
            throw new WiagiStorageCorruptedException("Corrupted income entry detected, error with recurrence " +
                    "frequency!");
        }

        LocalDate lastRecurred = null;
        if (!entryData[LOAD_LAST_RECURRED_INDEX].equals(NO_RECURRENCE)) {
            lastRecurred = LocalDate.parse(entryData[LOAD_LAST_RECURRED_INDEX]);
        }

        if (recurrenceFrequency != RecurrenceFrequency.NONE) {
            if (lastRecurred == null) {
                throw new WiagiStorageCorruptedException("Corrupted income entry detected, error with last " +
                        "recurred date!");
            }
        }
        assert recurrenceFrequency != RecurrenceFrequency.NONE || lastRecurred != null : "Corrupted " +
                "income entry detected, error with recurred date";

        int dayOfRecurrence;
        try {
            dayOfRecurrence = Integer.parseInt(entryData[LOAD_DAY_OF_RECURRENCE_INDEX]);
        } catch (NumberFormatException e) {
            throw new WiagiStorageCorruptedException("Corrupted income entry detected, error with day of " +
                    "recurrence!");
        }
        if (dayOfRecurrence < 1 || dayOfRecurrence > 31) {
            throw new WiagiStorageCorruptedException("Corrupted income entry detected, error with day of " +
                    "recurrence between 1 and 31!");
        }

        Income nextEntry = new Income(amount, description, date, tag, recurrenceFrequency, lastRecurred,
                dayOfRecurrence);
        Storage.incomes.add(nextEntry);
    }
}
