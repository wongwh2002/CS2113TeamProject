package seedu.storage;

import seedu.classes.WiagiLogger;
import seedu.type.Income;
import seedu.type.IncomeList;
import seedu.classes.Ui;
import seedu.exception.WiagiStorageCorruptedException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;

import static seedu.classes.Constants.SAVE_INCOME_FILE_ERROR;
import static seedu.classes.Constants.LOAD_INCOME_FILE_ERROR;
import static seedu.classes.Constants.STORAGE_SEPARATOR;

/**
 * Manages saving and loading of income data to and from a file.
 */
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
        try {
            WiagiLogger.logger.log(Level.INFO, "Starting to save incomes...");
            FileWriter fw = getFileWriter(incomes);
            fw.close();
            WiagiLogger.logger.log(Level.INFO, "Successfully saved incomes to file");
        } catch (IOException e) {
            WiagiLogger.logger.log(Level.WARNING, "Unable to save incomes file", e);
            Ui.printWithTab(SAVE_INCOME_FILE_ERROR);
        }
        assert new File(INCOMES_FILE_PATH).exists() : "Incomes file should exist after saving";
        WiagiLogger.logger.log(Level.INFO, "Finish saving incomes file");
    }

    private static FileWriter getFileWriter(IncomeList incomes) throws IOException {
        FileWriter fw = new FileWriter(INCOMES_FILE_PATH);
        for (Income income : incomes) {
            String incomeEntry = income.getAmount() + STORAGE_SEPARATOR + income.getDescription() +
                    STORAGE_SEPARATOR + income.getDate() + STORAGE_SEPARATOR + income.getTag() + STORAGE_SEPARATOR +
                    income.getRecurrenceFrequency() + STORAGE_SEPARATOR + income.getLastRecurrence() +
                    STORAGE_SEPARATOR + income.getDayOfRecurrence();
            fw.write(incomeEntry + System.lineSeparator());
        }
        return fw;
    }

    /**
     * Loads the income data from a file into the application's income list.
     * If no file exists, a new one is created.
     */
    static void load() {
        WiagiLogger.logger.log(Level.INFO, "Starting to load incomes...");
        long counter = 0;
        try {
            if (createNewFileIfNotExists()) {
                return;
            }
            File incomeFile = new File(INCOMES_FILE_PATH);
            Scanner incomeReader = new Scanner(incomeFile);
            while (incomeReader.hasNext()) {
                String newEntry = incomeReader.nextLine();
                counter++;
                processEntry(newEntry, counter);
            }
            incomeReader.close();
            WiagiLogger.logger.log(Level.INFO, "Successfully loaded incomes from file");
        } catch (IOException e) {
            handleIOException(e);
        } catch (NoSuchElementException e) {
            handleNoSuchElementException(e);
        }
        assert Storage.incomes.size() > 0 : "Incomes list should not be empty after loading";
        WiagiLogger.logger.log(Level.INFO, "Finish loading incomes file.");
    }

    private static boolean createNewFileIfNotExists() throws IOException {
        if (new File(INCOMES_FILE_PATH).createNewFile()) {
            WiagiLogger.logger.log(Level.INFO, "Incomes file does not exist, created a new file");
            return true;
        }
        return false;
    }

    private static void processEntry(String newEntry, long counter) {
        try {
            Income nextEntry = (Income) storageUtils.parseEntry(newEntry);
            Storage.incomes.add(nextEntry);
        } catch (WiagiStorageCorruptedException e) {
            handleCorruptedEntry(e, counter);
        }
    }

    private static void handleIOException(IOException e) {
        WiagiLogger.logger.log(Level.WARNING, "Unable to open incomes file", e);
        Ui.printWithTab(LOAD_INCOME_FILE_ERROR);
    }

    private static void handleNoSuchElementException(NoSuchElementException e) {
        WiagiLogger.logger.log(Level.WARNING, "Incomes file is empty", e);
        File incomeFile = new File(INCOMES_FILE_PATH);
        incomeFile.delete();
    }

    private static void handleCorruptedEntry(WiagiStorageCorruptedException e, long counter) {
        WiagiLogger.logger.log(Level.WARNING, "Corrupted income entry detected at line " + counter, e);
        Ui.printWithTab(e.getMessage());
        Ui.printWithTab("Detected at line " + counter + " in the incomes file.");
        Ui.printWithTab("Deleting corrupted entry...");
    }
}
