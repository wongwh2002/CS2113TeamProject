package seedu.storage;

import seedu.classes.WiagiLogger;
import seedu.commands.BudgetCommand;
import seedu.exception.WiagiStorageCorruptedException;
import seedu.type.Spending;
import seedu.type.SpendingList;
import seedu.classes.Ui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
            handleWriteFile(spendings);
        } catch (IOException e) {
            WiagiLogger.logger.log(Level.WARNING, "Unable to save spendings file", e);
            Ui.printWithTab(SAVE_SPENDING_FILE_ERROR);
        }
        WiagiLogger.logger.log(Level.INFO, "Finish saving spendings file");
    }

    private static void handleWriteFile(SpendingList spendings) throws IOException {
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
        fw.close();
    }

    /**
     * Loads the spending data from a file into the application's spending list.
     * If no file exists, a new one is created.
     */
    static void load() {
        WiagiLogger.logger.log(Level.INFO, "Starting to load spendings...");
        long counter = 0;
        try {
            if (new File(SPENDINGS_FILE_PATH).createNewFile()) {
                emptyFileErrorHandling();
                return;
            }
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
            WiagiLogger.logger.log(Level.INFO, "Successfully loaded spendings from file");
        } catch (IOException e) {
            WiagiLogger.logger.log(Level.WARNING, "Unable to open incomes file", e);
            Ui.printWithTab(LOAD_SPENDING_FILE_ERROR);
        } catch (NoSuchElementException e) {
            emptyFileErrorHandling();
        }
        WiagiLogger.logger.log(Level.INFO, "Finish loading spendings file.");
    }

    private static void loadBudgets(String[] budgetDetails) throws NoSuchElementException {
        if (budgetDetails.length != 3) {
            WiagiLogger.logger.log(Level.WARNING, "Corrupted budget details found in spendings file");
            Ui.printWithTab(LOAD_SPENDING_FILE_ERROR);
            throw new NoSuchElementException("Corrupted spending file, error with budget details");
        }
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

    private static void handleCorruptedEntry(WiagiStorageCorruptedException e, long counter) {
        WiagiLogger.logger.log(Level.WARNING, "Corrupted entry found in spendings file at line " + counter, e);
        Ui.handleCorruptedEntry(e, counter);
    }
}
