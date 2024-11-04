package seedu.storage;

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

import static seedu.classes.Constants.LOAD_DAILY_BUDGET_INDEX;
import static seedu.classes.Constants.LOAD_MONTHLY_BUDGET_INDEX;
import static seedu.classes.Constants.LOAD_SPENDING_FILE_ERROR;
import static seedu.classes.Constants.LOAD_YEARLY_BUDGET_INDEX;
import static seedu.classes.Constants.SAVE_SPENDING_FILE_ERROR;
import static seedu.classes.Constants.STORAGE_LOAD_SEPARATOR;
import static seedu.classes.Constants.STORAGE_SEPARATOR;
import static seedu.classes.Constants.LOAD_AMOUNT_INDEX;
import static seedu.classes.Constants.LOAD_DATE_INDEX;
import static seedu.classes.Constants.LOAD_DAY_OF_RECURRENCE_INDEX;
import static seedu.classes.Constants.LOAD_DESCRIPTION_INDEX;
import static seedu.classes.Constants.LOAD_LAST_RECURRED_INDEX;
import static seedu.classes.Constants.LOAD_RECURRENCE_INDEX;
import static seedu.classes.Constants.LOAD_TAG_INDEX;
import static seedu.classes.Constants.NO_RECURRENCE;

/**
 * Manages saving and loading of spending data to and from a file.
 */
public class SpendingListStorage {
    private static final String SPENDINGS_FILE_PATH = "./spendings.txt";

    /**
     * Saves the spending list, including each spending entry and budget details, to a file.
     *
     * @param spendings the SpendingList to be saved.
     */
    static void save(SpendingList spendings) {
        try {
            FileWriter fw = new FileWriter(SPENDINGS_FILE_PATH);
            String budgetDetails = spendings.getDailyBudget() + STORAGE_SEPARATOR +
                    spendings.getMonthlyBudget() + STORAGE_SEPARATOR + spendings.getYearlySpending();
            fw.write(budgetDetails + System.lineSeparator());
            for (Spending spending : spendings) {
                String singleEntry = spending.getAmount() + STORAGE_SEPARATOR + spending.getDescription() +
                        STORAGE_SEPARATOR + spending.getDate() + STORAGE_SEPARATOR + spending.getTag() +
                        STORAGE_SEPARATOR + spending.getRecurrenceFrequency() + STORAGE_SEPARATOR +
                        spending.getLastRecurrence() + STORAGE_SEPARATOR + spending.getDayOfRecurrence();
                fw.write(singleEntry + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            Ui.printWithTab(SAVE_SPENDING_FILE_ERROR);
        }
    }

    /**
     * Loads the spending data from a file into the application's spending list.
     * If no file exists, a new one is created.
     */
    static void load() {
        try {
            if (new File(SPENDINGS_FILE_PATH).createNewFile()) {
                return;
            }
            File spendingFile = new File(SPENDINGS_FILE_PATH);
            Scanner spendingReader = new Scanner(spendingFile);
            String[] budgetDetails = spendingReader.nextLine().split(STORAGE_LOAD_SEPARATOR);
            Storage.spendings.setDailyBudget(Double.parseDouble(budgetDetails[LOAD_DAILY_BUDGET_INDEX]));
            Storage.spendings.setMonthlyBudget(Double.parseDouble(budgetDetails[LOAD_MONTHLY_BUDGET_INDEX]));
            Storage.spendings.setYearlyBudget(Double.parseDouble(budgetDetails[LOAD_YEARLY_BUDGET_INDEX]));
            while (spendingReader.hasNext()) {
                String newEntry = spendingReader.nextLine();
                String[] entryData = newEntry.split(STORAGE_LOAD_SEPARATOR);
                LocalDate date = LocalDate.parse(entryData[LOAD_DATE_INDEX]);
                LocalDate lastRecurred = null;
                if (!entryData[LOAD_LAST_RECURRED_INDEX].equals(NO_RECURRENCE)) {
                    lastRecurred = LocalDate.parse(entryData[LOAD_LAST_RECURRED_INDEX]);
                }
                Spending nextEntry = new Spending(Double.parseDouble(entryData[LOAD_AMOUNT_INDEX]),
                        entryData[LOAD_DESCRIPTION_INDEX], date, entryData[LOAD_TAG_INDEX],
                        RecurrenceFrequency.valueOf(entryData[LOAD_RECURRENCE_INDEX]),
                        lastRecurred, Integer.parseInt(entryData[LOAD_DAY_OF_RECURRENCE_INDEX]));
                Storage.spendings.add(nextEntry);
            }
        } catch (IOException e) {
            Ui.printWithTab(LOAD_SPENDING_FILE_ERROR);
        } catch (NoSuchElementException e) {
            File spendingFile = new File(SPENDINGS_FILE_PATH);
            spendingFile.delete();
        }
    }
}
