package seedu.storage;

import org.junit.jupiter.api.Test;
import seedu.classes.Ui;
import seedu.recurrence.RecurrenceFrequency;
import seedu.type.Income;
import seedu.type.IncomeList;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.classes.Constants.NEXT_LINE;
import static seedu.classes.Constants.SAVE_INCOME_FILE_ERROR;
import static seedu.classes.Constants.TAB;
import static seedu.classes.Constants.TODAY;


@TestMethodOrder(OrderAnnotation.class)
public class IncomeListStorageTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;


    // Needs to be run sequentially
    @Order(2)
    @Test
    public void save_existingList_success() {
        IncomeList incomes = new IncomeList();
        incomes.add(new Income(10, "savings", TODAY, "", RecurrenceFrequency.NONE,
                null, 1));
        IncomeListStorage.save(incomes);
        assertTrue(new File("./incomes.txt").exists());
    }

    @Order(3)
    @Test
    public void load_existingFile_success() {
        IncomeListStorage.load();
        assertEquals( 1, Storage.incomes.size());
        Storage.incomes.clear();
    }

    //@@author wongwh2002
    @Order(4)
    @Test
    public void save_multipleEntries_success() {
        IncomeList incomes = new IncomeList();
        incomes.add(new Income(10, "savings", TODAY, "", RecurrenceFrequency.NONE,
                null, 1));
        incomes.add(new Income(20, "investment", TODAY, "",
                RecurrenceFrequency.MONTHLY, TODAY, 15));
        IncomeListStorage.save(incomes);
        assertTrue(new File("./incomes.txt").exists());
    }

    //@@author wongwh2002
    @Order(5)
    @Test
    public void load_multipleEntries_success() {
        IncomeListStorage.load();
        assertEquals(2, Storage.incomes.size());
        Storage.incomes.clear();
    }

    //@@author wongwh2002
    @Order(6)
    @Test
    public void load_multipleLinesFromFile_success() {
        File incomeFile = new File("./incomes.txt");
        if (incomeFile.exists()) {
            incomeFile.delete();
        }
        try {
            FileWriter writer = new FileWriter(incomeFile);
            writer.write("10.0|savings|2024-11-09||NONE|null|1\n");
            writer.write("20.0|investment|2024-11-09||MONTHLY|2024-11-09|15\n");
            writer.close();
        } catch (IOException e) {
            Ui.printWithTab("load_multipleLinesfromFile_success() fails due to fileWriter IOException");
        }
        IncomeListStorage.load();
        assertEquals(2, Storage.incomes.size());
        Storage.incomes.clear(); //resets income list for next test
    }

    //@@author wongwh2002
    @Test
    public void save_nullList_nullIncomeListMessage() {
        try {
            IncomeListStorage.save(null);
        } catch (AssertionError e) {
            assertEquals("IncomeList should not be null", e.getMessage());
        }
    }

    //@@author wongwh2002
    @Test
    public void load_corruptedLinesFromFiles_corruptedIncomeEntryMessage() {
        File incomeFile = new File("./incomes.txt");
        if (incomeFile.exists()) {
            incomeFile.delete();
        }
        try {
            FileWriter writer = new FileWriter(incomeFile);
            writer.write("10.0|savings|2024-11-09||NONE|null\n");
            writer.write("20.0|investment|2024-11-09||MONTHLY|2024-11-09|15\n");
            writer.close();
        } catch (IOException e) {
            Ui.printWithTab("load_multipleLinesfromFile_success() fails due to fileWriter IOException");
        }
        IncomeListStorage.load();
        assertEquals(TAB + "Corrupted income entry detected, supposed to have 7 parameters!" +
                NEXT_LINE +
                TAB + "Detected at line 1 in the incomes file." + NEXT_LINE +
                TAB + "Deleting corrupted entry..." + NEXT_LINE ,outContent.toString());
        assertEquals(1, Storage.incomes.size());
        Storage.incomes.clear(); //resets income list for next test
    }

    @Test
    public void load_emptyFile_noIncomesLoaded() {
        File incomeFile = new File("./incomes.txt");
        if (incomeFile.exists()) {
            incomeFile.delete();
        }
        try {
            incomeFile.createNewFile();
        } catch (IOException e) {
            Ui.printWithTab("Failed to create empty incomes file");
        }
        IncomeListStorage.load();
        assertEquals(0, Storage.incomes.size());
    }

    //@@author wongwh2002
    @Test
    public void load_noFile_successCreatesNewFile() {
        File incomeFile = new File("./incomes.txt");
        if (incomeFile.exists()) {
            incomeFile.delete();
        }
        IncomeListStorage.save(new IncomeList());
        assertTrue(incomeFile.exists());
        IncomeListStorage.load();
        assertEquals(0, Storage.incomes.size());
    }

    @Test
    public void save_ioExceptionDuringSave_saveIncomeFileErrorMessage() {
        IncomeList incomes = new IncomeList();
        incomes.add(new Income(10, "savings", TODAY, "", RecurrenceFrequency.NONE, null, 1));

        File incomeFile = new File("./incomes.txt");
        incomeFile.setReadOnly(); // read-only to trigger IOException
        IncomeListStorage.save(incomes);
        assertEquals(SAVE_INCOME_FILE_ERROR, outContent.toString().trim()); //remove TAB and newline

        incomeFile.setWritable(true); //reset file permission
        incomes.clear();
    }

    //@@author wongwh2002
    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restore() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

}
