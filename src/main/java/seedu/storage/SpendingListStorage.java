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
import java.util.Scanner;
import java.util.logging.Level;

import static seedu.classes.Constants.LOAD_DAILY_BUDGET_INDEX;
import static seedu.classes.Constants.LOAD_MONTHLY_BUDGET_INDEX;
import static seedu.classes.Constants.LOAD_SPENDING_FILE_ERROR;
import static seedu.classes.Constants.LOAD_YEARLY_BUDGET_INDEX;
import static seedu.classes.Constants.NEXT_LINE;
import static seedu.classes.Constants.MAX_LIST_TOTAL_AMOUNT;
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
        assert spendings != null : "SpendingList should not be null";
        WiagiLogger.logger.log(Level.INFO, "Starting to save spendings...");
        try {
            handleWriteFile(spendings);
            WiagiLogger.logger.log(Level.INFO, "Successfully saved spendings file");
        } catch (IOException e) {
            WiagiLogger.logger.log(Level.WARNING, "Unable to save spendings file", e);
            Ui.printWithTab(SAVE_SPENDING_FILE_ERROR);
        }
        assert new File(SPENDINGS_FILE_PATH).exists() : "Spendings file should exist after saving";
        WiagiLogger.logger.log(Level.INFO, "Finish saving spendings file");
    }

    //@@author wongwh2002
    private static void handleWriteFile(SpendingList spendings) throws IOException {
        FileWriter spendingFile = new FileWriter(SPENDINGS_FILE_PATH);
        String budgetDetails = spendings.getDailyBudget() + STORAGE_SEPARATOR +
                spendings.getMonthlyBudget() + STORAGE_SEPARATOR + spendings.getYearlyBudget();
        spendingFile.write(budgetDetails + NEXT_LINE);
        for (Spending spending : spendings) {
            String singleEntry = spending.getAmount() + STORAGE_SEPARATOR + spending.getDescription() +
                    STORAGE_SEPARATOR + spending.getDate() + STORAGE_SEPARATOR + spending.getTag() +
                    STORAGE_SEPARATOR + spending.getRecurrenceFrequency() + STORAGE_SEPARATOR +
                    spending.getLastRecurrence() + STORAGE_SEPARATOR + spending.getDayOfRecurrence();
            spendingFile.write(singleEntry + NEXT_LINE);
        }
        spendingFile.close();
    }

    /**
     * Loads the spending data from a file into the application's spending list.
     * If no file exists, a new one is created.
     */
    //@@author wongwh2002
    static void load() {
        WiagiLogger.logger.log(Level.INFO, "Starting to load spendings...");
        File spendingFile = new File(SPENDINGS_FILE_PATH);
        int errorEntryNumber = 0;
        try {
            if (!spendingFile.exists() || spendingFile.length() == 0) {
                loadingFileErrorHandling();
                return;
            }
            Scanner spendingReader = new Scanner(spendingFile);
            assert spendingReader.hasNext() : "file is not empty";
            String budgetDetails = spendingReader.nextLine();
            loadBudgets(budgetDetails);
            while (spendingReader.hasNext()) {
                String newEntry = spendingReader.nextLine();
                errorEntryNumber++;
                processEntry(newEntry, errorEntryNumber);
            }
            spendingReader.close();
            WiagiLogger.logger.log(Level.INFO, "Successfully loaded spendings from file");
        } catch (IOException e) {
            WiagiLogger.logger.log(Level.WARNING, "Unable to open spendings file", e);
            Ui.printWithTab(LOAD_SPENDING_FILE_ERROR);
        }
        WiagiLogger.logger.log(Level.INFO, "Finish loading spendings file.");
    }

    private static void loadBudgets(String budgetDetail) {
        String[] budgetDetails = budgetDetail.split(STORAGE_LOAD_SEPARATOR);
        if (budgetDetails.length != 3) {
            WiagiLogger.logger.log(Level.WARNING, "Corrupted budget details found in spendings file");
            loadingFileErrorHandling();
            processEntry(budgetDetail,1);
            return;
        }
        try {
            double dailyBudget = Double.parseDouble(budgetDetails[LOAD_DAILY_BUDGET_INDEX]);
            double monthlyBudget = Double.parseDouble(budgetDetails[LOAD_MONTHLY_BUDGET_INDEX]);
            double yearlyBudget = Double.parseDouble(budgetDetails[LOAD_YEARLY_BUDGET_INDEX]);
            checkBudgetLogicError(dailyBudget, monthlyBudget, yearlyBudget);
            Storage.spendings.setDailyBudget(dailyBudget);
            Storage.spendings.setMonthlyBudget(monthlyBudget);
            Storage.spendings.setYearlyBudget(yearlyBudget);
        } catch (NumberFormatException | WiagiStorageCorruptedException e) {
            loadingFileErrorHandling();
        }
    }

    private static void checkBudgetLogicError(double dailyBudget, double monthlyBudget, double yearlyBudget)
            throws WiagiStorageCorruptedException {
        if (yearlyBudget > MAX_LIST_TOTAL_AMOUNT || dailyBudget > monthlyBudget || monthlyBudget > yearlyBudget) {
            throw new WiagiStorageCorruptedException("Budget in storage is corrupted");
        }
    }

    private static void loadingFileErrorHandling() {
        File passwordFile = new File(PASSWORD_FILE_PATH);
        if (passwordFile.exists() && passwordFile.length() != 0) {
            Ui.errorLoadingBudgetMessage();
            BudgetCommand.initialiseBudget(Storage.spendings);
        }
    }

    //@@author wongwh2002
    private static void processEntry(String newEntry, int counter) {
        try {
            Spending nextEntry = (Spending) storageUtils.parseEntry(newEntry);
            Storage.spendings.add(nextEntry);
        } catch (WiagiStorageCorruptedException e) {
            handleCorruptedEntry(e, counter);
        }
    }

    //@@author wongwh2002
    private static void handleCorruptedEntry(WiagiStorageCorruptedException e, int counter) {
        WiagiLogger.logger.log(Level.WARNING, "Corrupted entry found in spendings file at line " + counter, e);
        Ui.handleCorruptedEntry(e, counter, "spendings");
    }
}
