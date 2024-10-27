package seedu.storage;

import seedu.recurrence.RecurrenceFrequency;
import seedu.type.Income;
import seedu.type.IncomeList;
import seedu.classes.Ui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static seedu.classes.Constants.CSV_SEPARATOR;
import static seedu.classes.Constants.LOAD_AMOUNT_INDEX;
import static seedu.classes.Constants.LOAD_DAY_OF_RECURRENCE_INDEX;
import static seedu.classes.Constants.LOAD_DESCRIPTION_INDEX;
import static seedu.classes.Constants.LOAD_RECURRENCE_INDEX;
import static seedu.classes.Constants.LOAD_TAG_INDEX;

public class IncomeListStorage {
    private static final String INCOME_FILE_PATH = "./incomes.csv";

    static void save(IncomeList incomes) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(INCOME_FILE_PATH),
                    StandardCharsets.UTF_8));
            for (Income income : incomes) {
                String incomeEntry = income.getAmount() + CSV_SEPARATOR + income.getDescription() +
                        CSV_SEPARATOR + income.getDate() + CSV_SEPARATOR + income.getTag() + CSV_SEPARATOR +
                        income.getRecurrenceFrequency() + CSV_SEPARATOR + income.getLastRecurrence() +
                        CSV_SEPARATOR + income.getDayOfRecurrence();
                bw.write(incomeEntry);
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (IOException e){
            Ui.printWithTab("An error has occurred when saving file!");
        }
    }

    static void load() {
        String newEntry = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(INCOME_FILE_PATH));
            while ((newEntry = br.readLine()) != null) {
                String[] entryData = newEntry.split(",");
                LocalDate date = LocalDate.parse(entryData[2]);
                LocalDate lastRecurred = null;
                if (!entryData[5].equals("null")) {
                    lastRecurred = LocalDate.parse(entryData[5]);
                }
                Income nextEntry = new Income(Double.parseDouble(entryData[LOAD_AMOUNT_INDEX]),
                        entryData[LOAD_DESCRIPTION_INDEX], date, entryData[LOAD_TAG_INDEX],
                        RecurrenceFrequency.valueOf(entryData[LOAD_RECURRENCE_INDEX]),
                        lastRecurred, Integer.parseInt(entryData[LOAD_DAY_OF_RECURRENCE_INDEX]));
                Storage.incomes.add(nextEntry);
            }
        } catch (IOException e) {
            Ui.printWithTab("An error has occurred when saving file!");
        }
    }
}
