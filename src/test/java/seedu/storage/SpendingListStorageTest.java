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
import static seedu.classes.Constants.VALID_TEST_DATE;

public class SpendingListStorageTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private File spendingsFile = new File("./spendings.txt");
    private File passwordFile = new File("./password.txt");
    private FileWriter spendingsFileWriter;
    private FileWriter passwordFileWriter;

    @BeforeEach
    public void setUp() throws IOException {
        spendingsFileWriter = new FileWriter("./spendings.txt");
        passwordFileWriter = new FileWriter("./password.txt");
        spendingsFile.createNewFile();
        passwordFile.createNewFile();
        passwordFileWriter.write("1");
        passwordFileWriter.close();
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
    public void save_existingList_success() {
        spendingsFile.delete();
        SpendingList spendings = new SpendingList();
        spendings.add(new Spending(10, "macs", VALID_TEST_DATE, "", RecurrenceFrequency.NONE, null, 1));
        SpendingListStorage.save(spendings);
        assertTrue(new File("./spendings.txt").exists());
    }

    @Test
    public void load_existingFile_success() {
        try {
            FileWriter spendingsFileWriter = new FileWriter("./spendings.txt");
            FileWriter passwordFileWriter = new FileWriter("./password.txt");
            passwordFile.createNewFile();
            passwordFileWriter.write("1");
            passwordFileWriter.close();
            spendingsFileWriter.write("1.0|1.0|1.0" + System.lineSeparator() + "10.0|macs|2024-11-09||NONE|null|1");
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
            FileWriter passwordFileWriter = new FileWriter("./password.txt");
            passwordFile.createNewFile();
            passwordFileWriter.write("1");
            passwordFileWriter.close();
            spendingsFileWriter.write("10.0|1.0|1.0" + System.lineSeparator());
            spendingsFileWriter.close();
            Ui.userInputForTest(1 + System.lineSeparator() + 2 + System.lineSeparator() + 3);
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
            FileWriter passwordFileWriter = new FileWriter("./password.txt");
            passwordFile.createNewFile();
            passwordFileWriter.write("1");
            passwordFileWriter.close();
            spendingsFileWriter.write("1.0|10.0|1.0" + System.lineSeparator());
            spendingsFileWriter.close();
            Ui.userInputForTest(1 + System.lineSeparator() + 2 + System.lineSeparator() + 3);
            SpendingListStorage.load();
            assertEquals(1, Storage.spendings.getDailyBudget());
            assertEquals(2, Storage.spendings.getMonthlyBudget());
            assertEquals(3, Storage.spendings.getYearlyBudget());
        } catch (IOException e) {
            Ui.printWithTab("error occured with load_userEditMonthlyMoreThanYearly_wrongBudgetNotSet() test");
        }
    }

    @Test
    public void load_userEditMonthlyLessThanDaily_wrongBudgetNotSet() throws IOException {
        try {
            FileWriter spendingsFileWriter = new FileWriter("./spendings.txt");
            FileWriter passwordFileWriter = new FileWriter("./password.txt");
            passwordFile.createNewFile();
            passwordFileWriter.write("1");
            passwordFileWriter.close();
            spendingsFileWriter.write("15.0|10.0|100.0" + System.lineSeparator());
            spendingsFileWriter.close();
            Ui.userInputForTest(1 + System.lineSeparator() + 2 + System.lineSeparator() + 3);
            SpendingListStorage.load();
            assertEquals(1, Storage.spendings.getDailyBudget());
            assertEquals(2, Storage.spendings.getMonthlyBudget());
            assertEquals(3, Storage.spendings.getYearlyBudget());
        } catch (IOException e) {
            Ui.printWithTab("error occured with load_userEditMonthlyLessThanDaily_wrongBudgetNotSet() test");
        }
    }
}
