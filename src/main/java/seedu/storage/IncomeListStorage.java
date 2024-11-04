package seedu.storage;

import seedu.classes.WiagiLogger;
import seedu.recurrence.RecurrenceFrequency;
import seedu.type.Income;
import seedu.type.IncomeList;
import seedu.classes.Ui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;

import static seedu.classes.Constants.LOAD_INCOME_FILE_ERROR;
import static seedu.classes.Constants.SAVE_INCOME_FILE_ERROR;
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

public class IncomeListStorage {
    private static final String INCOMES_FILE_PATH = "./incomes.txt";

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
        } catch (IOException e){
            WiagiLogger.logger.log(Level.WARNING, "Unable to save incomes file", e);
            Ui.printWithTab(SAVE_INCOME_FILE_ERROR);
        }
        WiagiLogger.logger.log(Level.INFO, "Finish saving incomes file");
    }

    static void load() {
        WiagiLogger.logger.log(Level.INFO, "Starting to load incomes...");
        try {
            if (new File(INCOMES_FILE_PATH).createNewFile()) {
                return;
            }
            File incomeFile = new File(INCOMES_FILE_PATH);
            Scanner incomeReader = new Scanner(incomeFile);
            while (incomeReader.hasNext()) {
                String newEntry = incomeReader.nextLine();
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
        } catch (IOException e) {
            WiagiLogger.logger.log(Level.WARNING, "Unable to open incomes file", e);
            Ui.printWithTab(LOAD_INCOME_FILE_ERROR);
        } catch (NoSuchElementException e) {
            WiagiLogger.logger.log(Level.WARNING, "Incomes file is empty", e);
            File incomeFile = new File(INCOMES_FILE_PATH);
            incomeFile.delete();
        }
        WiagiLogger.logger.log(Level.INFO, "Finish loading incomes file.");
    }
}
