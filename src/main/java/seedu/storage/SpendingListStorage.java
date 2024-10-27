package seedu.storage;

import seedu.recurrence.RecurrenceFrequency;
import seedu.type.Spending;
import seedu.type.SpendingList;
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
import static seedu.classes.Constants.LOAD_DATE_INDEX;
import static seedu.classes.Constants.LOAD_DAY_OF_RECURRENCE_INDEX;
import static seedu.classes.Constants.LOAD_DESCRIPTION_INDEX;
import static seedu.classes.Constants.LOAD_LAST_RECURRED_INDEX;
import static seedu.classes.Constants.LOAD_RECURRENCE_INDEX;
import static seedu.classes.Constants.LOAD_TAG_INDEX;

public class SpendingListStorage {
    private static final String SPENDINGS_FILE_PATH = "./spendings.csv";

    static void save(SpendingList spendings) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(SPENDINGS_FILE_PATH),
                    StandardCharsets.UTF_8));
            for (Spending spending : spendings) {
                String singleEntry = spending.getAmount() + CSV_SEPARATOR + spending.getDescription() +
                        CSV_SEPARATOR + spending.getDate() + CSV_SEPARATOR + spending.getTag() + CSV_SEPARATOR +
                        spending.getRecurrenceFrequency() + CSV_SEPARATOR + spending.getLastRecurrence() +
                        CSV_SEPARATOR + spending.getDayOfRecurrence();
                bw.write(singleEntry);
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
            BufferedReader br = new BufferedReader(new FileReader(SPENDINGS_FILE_PATH));
            while ((newEntry = br.readLine()) != null) {
                String[] entryData = newEntry.split(",");
                LocalDate date = LocalDate.parse(entryData[LOAD_DATE_INDEX]);
                LocalDate lastRecurred = null;
                if (!entryData[LOAD_LAST_RECURRED_INDEX].equals("null")) {
                    lastRecurred = LocalDate.parse(entryData[LOAD_LAST_RECURRED_INDEX]);
                }
                Spending nextEntry =  new Spending(Double.parseDouble(entryData[LOAD_AMOUNT_INDEX]),
                        entryData[LOAD_DESCRIPTION_INDEX], date, entryData[LOAD_TAG_INDEX],
                        RecurrenceFrequency.valueOf(entryData[LOAD_RECURRENCE_INDEX]),
                        lastRecurred, Integer.parseInt(entryData[LOAD_DAY_OF_RECURRENCE_INDEX]));
                Storage.spendings.add(nextEntry);
            }
        } catch (IOException e) {
            Ui.printWithTab("An error has occurred when saving file!");
        }
    }
}
