package seedu.storage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.classes.Ui;
import seedu.recurrence.RecurrenceFrequency;
import seedu.type.Spending;
import seedu.type.SpendingList;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.classes.Constants.NEXT_LINE;
import static seedu.classes.Constants.SAVE_SPENDING_FILE_ERROR;
import static seedu.classes.Constants.TAB;
import static seedu.classes.Constants.TODAY;


public class SpendingListStorageTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private final File spendingsFile = new File("./spendings.txt");
    private final File passwordFile = new File("./password.txt");

    private void initialisePasswordFile() throws IOException {
        FileWriter passwordFileWriter = new FileWriter("./password.txt");
        passwordFile.createNewFile();
        passwordFileWriter.write("0");
        passwordFileWriter.close();
    }

    @BeforeEach
    public void setUp() throws IOException {
        spendingsFile.createNewFile();
        Storage.spendings = new SpendingList();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restore() throws IOException {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void load_corruptedEntriesList_entryDiscardedMessage() {
        try {
            FileWriter spendingsFileWriter = new FileWriter("./spendings.txt");
            initialisePasswordFile();
            spendingsFileWriter.write("1.0|1.0|1.0" + NEXT_LINE + "1.0|macs|wrong||NONE|null|1");
            spendingsFileWriter.close();
            SpendingListStorage.load();
            assertEquals(TAB + "Corrupted spending entry detected, error with date!" + NEXT_LINE
                    + TAB + "Detected at line 1 in the spendings file." + NEXT_LINE
                    + TAB + "Deleting corrupted entry..." + NEXT_LINE, outContent.toString());
        } catch (IOException e) {
            Ui.printWithTab("error occured with load_corruptedEntriesList_entryDiscardedMessage() test");
        }
    }

    @Test
    public void save_existingList_success() {
        spendingsFile.delete();
        SpendingList spendings = new SpendingList();
        spendings.add(new Spending(10, "macs", TODAY, "", RecurrenceFrequency.NONE, null, 1));
        SpendingListStorage.save(spendings);
        assertTrue(new File("./spendings.txt").exists());
    }

    @Test
    public void load_storageFileDeletedAndPasswordFileExist_initialiseBudget() {
        try {
            spendingsFile.delete();
            initialisePasswordFile();
            Ui.userInputForTest(1 + NEXT_LINE + 2 + NEXT_LINE + 3);
            SpendingListStorage.load();
            assertEquals(1, Storage.spendings.getDailyBudget());
            assertEquals(2, Storage.spendings.getMonthlyBudget());
            assertEquals(3, Storage.spendings.getYearlyBudget());
        } catch (IOException e) {
            Ui.printWithTab("error occured with load_storageFileDeletedAndPasswordFileExist_initialiseBudget() test");
        }
    }

    @Test
    public void load_storageFileDeletedAndPasswordFileEmpty_budgetNotInitialised() {
        try {
            spendingsFile.delete();
            passwordFile.createNewFile();
            FileWriter passwordFileWriter = new FileWriter("./password.txt");
            passwordFileWriter.write("");
            passwordFileWriter.close();
            SpendingListStorage.load();
            assertEquals(0, Storage.spendings.getDailyBudget());
            assertEquals(0, Storage.spendings.getMonthlyBudget());
            assertEquals(0, Storage.spendings.getYearlyBudget());
        } catch (IOException e) {
            Ui.printWithTab("error occured with load_storageFileDeletedAndPasswordFileDeleted_budgetNotInitialised() " +
                    "test");
        }
    }

    @Test
    public void load_existingFile_success() {
        try {
            FileWriter spendingsFileWriter = new FileWriter("./spendings.txt");
            initialisePasswordFile();
            spendingsFileWriter.write("1.0|1.0|1.0" + NEXT_LINE + "10.0|macs|2024-11-09||NONE|null|1");
            spendingsFileWriter.close();
            SpendingListStorage.load();
            assertEquals(Storage.spendings.size(), 1);
        } catch (IOException e) {
            Ui.printWithTab("error occured with load_exisitingFile_success() test");
        }
    }

    @Test
    public void load_userEditDailyMoreThanMonthly_wrongBudgetNotSet() {
        try {
            FileWriter spendingsFileWriter = new FileWriter("./spendings.txt");
            initialisePasswordFile();
            spendingsFileWriter.write("10.0|1.0|1.0" + NEXT_LINE);
            spendingsFileWriter.close();
            Ui.userInputForTest(1 + NEXT_LINE + 2 + NEXT_LINE + 3);
            SpendingListStorage.load();
            assertEquals(1, Storage.spendings.getDailyBudget());
            assertEquals(2, Storage.spendings.getMonthlyBudget());
            assertEquals(3, Storage.spendings.getYearlyBudget());
        } catch (IOException e) {
            Ui.printWithTab("error occured with load_userEditDailyMoreThanMonthly_wrongBudgetNotSet() test");
        }
    }

    @Test
    public void load_userEditMonthlyMoreThanYearly_wrongBudgetNotSet() {
        try {
            FileWriter spendingsFileWriter = new FileWriter("./spendings.txt");
            initialisePasswordFile();
            spendingsFileWriter.write("1.0|10.0|1.0" + NEXT_LINE);
            spendingsFileWriter.close();
            Ui.userInputForTest(1 + NEXT_LINE + 2 + NEXT_LINE + 3);
            SpendingListStorage.load();
            assertEquals(1, Storage.spendings.getDailyBudget());
            assertEquals(2, Storage.spendings.getMonthlyBudget());
            assertEquals(3, Storage.spendings.getYearlyBudget());
        } catch (IOException e) {
            Ui.printWithTab("error occured with load_userEditMonthlyMoreThanYearly_wrongBudgetNotSet() test");
        }
    }

    @Test
    public void load_userEditMonthlyLessThanDaily_wrongBudgetNotSet() {
        try {
            FileWriter spendingsFileWriter = new FileWriter("./spendings.txt");
            initialisePasswordFile();
            spendingsFileWriter.write("15.0|10.0|100.0" + NEXT_LINE);
            spendingsFileWriter.close();
            Ui.userInputForTest(1 + NEXT_LINE + 2 + NEXT_LINE + 3);
            SpendingListStorage.load();
            assertEquals(1, Storage.spendings.getDailyBudget());
            assertEquals(2, Storage.spendings.getMonthlyBudget());
            assertEquals(3, Storage.spendings.getYearlyBudget());
        } catch (IOException e) {
            Ui.printWithTab("error occured with load_userEditMonthlyLessThanDaily_wrongBudgetNotSet() test");
        }
    }

    @Test
    public void load_budgetLengthNotThree_newBudgetInitialised() {
        try {
            FileWriter spendingsFileWriter = new FileWriter("./spendings.txt");
            initialisePasswordFile();
            spendingsFileWriter.write("15.0|10.0" + NEXT_LINE);
            spendingsFileWriter.close();
            Ui.userInputForTest(1 + NEXT_LINE + 2 + NEXT_LINE + 3);
            SpendingListStorage.load();
            assertEquals(1, Storage.spendings.getDailyBudget());
            assertEquals(2, Storage.spendings.getMonthlyBudget());
            assertEquals(3, Storage.spendings.getYearlyBudget());
        } catch (IOException e) {
            Ui.printWithTab("error occured with load_budgetLengthNotThree_newBudgetInitialised() test");
        }
    }

    @Test
    public void load_spendingFileEmpty_newBudgetInitialised() {
        try {
            FileWriter spendingsFileWriter = new FileWriter("./spendings.txt");
            initialisePasswordFile();
            spendingsFileWriter.write("");
            spendingsFileWriter.close();
            Ui.userInputForTest(1 + NEXT_LINE + 2 + NEXT_LINE + 3);
            SpendingListStorage.load();
            assertEquals(1, Storage.spendings.getDailyBudget());
            assertEquals(2, Storage.spendings.getMonthlyBudget());
            assertEquals(3, Storage.spendings.getYearlyBudget());
        } catch (IOException e) {
            Ui.printWithTab("error occured with load_spendingFileEmpty_newBudgetInitialised() test");
        }
    }

    @Test
    public void save_ioExceptionDuringSave_saveSpendingFileErrorMessage() {
        SpendingList spendings = new SpendingList();
        spendings.add(new Spending(10, "kfc", TODAY, "", RecurrenceFrequency.NONE, null, 1));

        File spendingFile = new File("./spendings.txt");
        spendingFile.setReadOnly(); // read-only to trigger IOException
        SpendingListStorage.save(spendings);
        assertEquals(SAVE_SPENDING_FILE_ERROR, outContent.toString().trim()); //remove TAB and newline

        spendingFile.setWritable(true); //reset file permission
        spendings.clear();
    }
}
