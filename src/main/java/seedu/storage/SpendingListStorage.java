package seedu.storage;

import seedu.classes.WiagiLogger;
import seedu.commands.BudgetCommand;
import seedu.exception.WiagiStorageCorruptedException;
import seedu.recurrence.RecurrenceFrequency;
import seedu.type.Spending;
import seedu.type.SpendingList;
import seedu.classes.Ui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;

import static seedu.classes.Constants.LOAD_DAILY_BUDGET_INDEX;
import static seedu.classes.Constants.LOAD_MONTHLY_BUDGET_INDEX;
import static seedu.classes.Constants.LOAD_SPENDING_FILE_ERROR;
import static seedu.classes.Constants.LOAD_YEARLY_BUDGET_INDEX;
import static seedu.classes.Constants.SAVE_SPENDING_FILE_ERROR;
import static seedu.classes.Constants.STORAGE_LOAD_SEPARATOR;
import static seedu.classes.Constants.STORAGE_SEPARATOR;
import static seedu.storage.LoginStorage.PASSWORD_FILE_PATH;

/**
 * Manages saving and loading of spending data to and from a file.
 */
public class SpendingListStorage {
    static final String SPENDINGS_FILE_PATH = "./spendings.txt";
    static LoadStorageCheck storageUtils = new LoadStorageCheck("spending");
    /**
     * Saves the spending list, including each spending entry and budget details, to a file.
     *
     * @param spendings the SpendingList to be saved.
     */
    static void save(SpendingList spendings) {
        WiagiLogger.logger.log(Level.INFO, "Starting to save spendings...");
        try {
            FileWriter fw = getFileWriter(spendings);
            fw.close();
        } catch (IOException e) {
            WiagiLogger.logger.log(Level.WARNING, "Unable to save spendings file", e);
            Ui.printWithTab(SAVE_SPENDING_FILE_ERROR);
        }
        WiagiLogger.logger.log(Level.INFO, "Finish saving spendings file");
    }

    private static FileWriter getFileWriter(SpendingList spendings) throws IOException {
        FileWriter fw = new FileWriter(SPENDINGS_FILE_PATH);
        String budgetDetails = spendings.getDailyBudget() + STORAGE_SEPARATOR +
                spendings.getMonthlyBudget() + STORAGE_SEPARATOR + spendings.getYearlyBudget();
        fw.write(budgetDetails + System.lineSeparator());
        for (Spending spending : spendings) {
            String singleEntry = spending.getAmount() + STORAGE_SEPARATOR + spending.getDescription() +
                    STORAGE_SEPARATOR + spending.getDate() + STORAGE_SEPARATOR + spending.getTag() +
                    STORAGE_SEPARATOR + spending.getRecurrenceFrequency() + STORAGE_SEPARATOR +
                    spending.getLastRecurrence() + STORAGE_SEPARATOR + spending.getDayOfRecurrence();
            fw.write(singleEntry + System.lineSeparator());
        }
        return fw;
    }

    /**
     * Loads the spending data from a file into the application's spending list.
     * If no file exists, a new one is created.
     */
    static void load() {
        WiagiLogger.logger.log(Level.INFO, "Starting to load incomes...");
        long counter = 0;
        try {
            if (createNewFileIfNotExists()) return;
            File spendingFile = new File(SPENDINGS_FILE_PATH);
            Scanner spendingReader = new Scanner(spendingFile);
            String[] budgetDetails = spendingReader.nextLine().split(STORAGE_LOAD_SEPARATOR);
            loadBudgets(budgetDetails);
            while (spendingReader.hasNext()) {
                String newEntry = spendingReader.nextLine();
                counter++;
                processEntry(newEntry, counter);
            }
            spendingReader.close();
            WiagiLogger.logger.log(Level.INFO, "Successfully loaded incomes from file");
        } catch (IOException e) {
            handleIOException(e);
        } catch (NoSuchElementException e) {
            handleNoSuchElementException(e);
        }
        assert Storage.spendings.size() > 0 : "Incomes list should not be empty after loading";
        WiagiLogger.logger.log(Level.INFO, "Finish loading incomes file.");
    }

    private static boolean createNewFileIfNotExists() throws IOException {
        if (new File(SPENDINGS_FILE_PATH).createNewFile()) {
            WiagiLogger.logger.log(Level.INFO, "Spending file does not exist, created a new file");
            return true;
        }
        return false;
    }

    private static void loadBudgets(String[] budgetDetails) {
        Storage.spendings.setDailyBudget(Double.parseDouble(budgetDetails[LOAD_DAILY_BUDGET_INDEX]));
        Storage.spendings.setMonthlyBudget(Double.parseDouble(budgetDetails[LOAD_MONTHLY_BUDGET_INDEX]));
        Storage.spendings.setYearlyBudget(Double.parseDouble(budgetDetails[LOAD_YEARLY_BUDGET_INDEX]));
    }

    private static void emptyFileErrorHandling() {
        File spendingFile = new File(SPENDINGS_FILE_PATH);
        spendingFile.delete();
        if (new File(PASSWORD_FILE_PATH).exists()) {
            Ui.errorLoadingBudgetMessage();
            BudgetCommand.initialiseBudget(Storage.spendings);
        }
    }

    private static void processEntry(String newEntry, long counter) {
        try {
            Spending nextEntry = (Spending) storageUtils.parseEntry(newEntry);
            Storage.spendings.add(nextEntry);
        } catch (WiagiStorageCorruptedException e) {
            handleCorruptedEntry(e, counter);
        }
    }

    private static void handleIOException(IOException e) {
        WiagiLogger.logger.log(Level.WARNING, "Unable to open spendings file", e);
        Ui.printWithTab(LOAD_SPENDING_FILE_ERROR);
    }

    private static void handleNoSuchElementException(NoSuchElementException e) {
        WiagiLogger.logger.log(Level.WARNING, "Spendings file is empty", e);
        File spendingFile = new File(SPENDINGS_FILE_PATH);
        spendingFile.delete();
    }

    private static void handleCorruptedEntry(WiagiStorageCorruptedException e, long counter) {
        WiagiLogger.logger.log(Level.WARNING, "Corrupted entry found in spendings file at line " + counter, e);
        Ui.printWithTab(e.getMessage());
        Ui.printWithTab("Detected at line " + counter + " in the spendings file.");
        Ui.printWithTab("Deleting corrupted entry...");
    }

}
