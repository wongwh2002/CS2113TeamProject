package seedu.storage;

import seedu.classes.WiagiLogger;
import seedu.type.Income;
import seedu.type.IncomeList;
import seedu.classes.Ui;
import seedu.exception.WiagiStorageCorruptedException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;

import static seedu.classes.Constants.NEXT_LINE;
import static seedu.classes.Constants.SAVE_INCOME_FILE_ERROR;
import static seedu.classes.Constants.LOAD_INCOME_FILE_ERROR;
import static seedu.classes.Constants.STORAGE_SEPARATOR;

/**
 * Manages saving and loading of income data to and from a file.
 */
//@@author wongwh2002
public class IncomeListStorage {
    static final String INCOMES_FILE_PATH = "./incomes.txt";

    static LoadStorageCheck storageUtils = new LoadStorageCheck("income");
    /**
     * Saves the income list, including each income entry, to a file.
     *
     * @param incomes the list of incomes to be saved
     */
    static void save(IncomeList incomes) {
        assert incomes != null : "IncomeList should not be null";
        WiagiLogger.logger.log(Level.INFO, "Starting to save incomes...");
        try {
            handleWriteFile(incomes);
            WiagiLogger.logger.log(Level.INFO, "Successfully saved incomes file");
        } catch (IOException e) {
            WiagiLogger.logger.log(Level.WARNING, "Unable to save incomes file", e);
            Ui.printWithTab(SAVE_INCOME_FILE_ERROR);
        }
        assert new File(INCOMES_FILE_PATH).exists() : "Incomes file should exist after saving";
        WiagiLogger.logger.log(Level.INFO, "Finish saving incomes file");
    }

    //@@author wongwh2002
    private static void handleWriteFile(IncomeList incomes) throws IOException {
        FileWriter incomeFileWriter = new FileWriter(INCOMES_FILE_PATH);
        for (Income income : incomes) {
            String incomeEntry = income.getAmount() + STORAGE_SEPARATOR + income.getDescription() +
                    STORAGE_SEPARATOR + income.getDate() + STORAGE_SEPARATOR + income.getTag() + STORAGE_SEPARATOR +
                    income.getRecurrenceFrequency() + STORAGE_SEPARATOR + income.getLastRecurrence() +
                    STORAGE_SEPARATOR + income.getDayOfRecurrence();
            incomeFileWriter.write(incomeEntry + NEXT_LINE);
        }
        incomeFileWriter.close();
    }

    /**
     * Loads the income data from a file into the application's income list.
     * If no file exists, a new one is created.
     */
    //@@author wongwh2002
    static void load() {
        WiagiLogger.logger.log(Level.INFO, "Starting to load incomes...");
        int errorEntryNumber = 0;
        File incomeFile = new File(INCOMES_FILE_PATH);
        try {
            if (!incomeFile.exists() || incomeFile.length() == 0) {
                return;
            }
            Scanner incomeReader = new Scanner(incomeFile);
            while (incomeReader.hasNext()) {
                String newEntry = incomeReader.nextLine();
                errorEntryNumber++;
                processEntry(newEntry, errorEntryNumber);
            }
            incomeReader.close();
            WiagiLogger.logger.log(Level.INFO, "Successfully loaded incomes from file");
        } catch (IOException e) {
            WiagiLogger.logger.log(Level.WARNING, "Unable to open incomes file", e);
            Ui.printWithTab(LOAD_INCOME_FILE_ERROR);
        }
        WiagiLogger.logger.log(Level.INFO, "Finish loading incomes file.");
    }

    //@@author wongwh2002
    private static void processEntry(String newEntry, long counter) {
        try {
            Income nextEntry = (Income) storageUtils.parseEntry(newEntry);
            Storage.incomes.add(nextEntry);
        } catch (WiagiStorageCorruptedException e) {
            handleCorruptedEntry(e, counter);
        }
    }

    //@@author wongwh2002
    private static void handleCorruptedEntry(WiagiStorageCorruptedException e, long counter) {
        WiagiLogger.logger.log(Level.WARNING, "Corrupted income entry detected at line " + counter, e);
        Ui.handleCorruptedEntry(e, counter, "incomes");
    }
}
