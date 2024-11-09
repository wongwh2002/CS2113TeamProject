package seedu.storage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import seedu.classes.Ui;
import seedu.exception.WiagiStorageCorruptedException;
import seedu.recurrence.RecurrenceFrequency;
import seedu.type.Income;
import seedu.type.IncomeList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.classes.Constants.VALID_TEST_DATE;

public class LoadStorageCheckTest {

    @Test
    public void parseEntry_validInput_success() {
        LoadStorageCheck storageUtils = new LoadStorageCheck("income");
        String newEntry = "10.0|savings|2024-11-09||NONE|null|1";
        Income income = (Income) storageUtils.parseEntry(newEntry);
        assertEquals(10.0, income.getAmount());
        assertEquals("savings", income.getDescription());
        assertEquals(VALID_TEST_DATE, income.getDate());
        assertEquals("", income.getTag());
        assertEquals(RecurrenceFrequency.NONE, income.getRecurrenceFrequency());
        assertNull(income.getLastRecurrence());
        assertEquals(1, income.getDayOfRecurrence());
    }

    @Test
    public void parseEntry_invalidEntryDataLength_storageCorruptedExceptionthrown() {
        LoadStorageCheck storageUtils = new LoadStorageCheck("income");
        String newEntry = "10.0|savings|2024-11-09||NONE|null";
        try {
            storageUtils.parseEntry(newEntry);
        } catch (WiagiStorageCorruptedException e) {
            assertEquals(storageUtils.corruptedErrorMessage + LoadStorageCheck.STORAGE_COMPULSORY_SIZE, e.getMessage());
        }
    }

    @Test
    public void parseEntry_amountNotNumber_storageCorruptedExceptionthrown() {
        LoadStorageCheck storageUtils = new LoadStorageCheck("income");
        String newEntry = "10.0a|savings|2024-11-09||NONE|null|1";
        try {
            storageUtils.parseEntry(newEntry);
        } catch (WiagiStorageCorruptedException e) {
            assertEquals(storageUtils.storageErrorMessage + "amount", e.getMessage());
        }
    }

    @Test
    public void parseEntry_amountLessThanZero_storageCorruptedExceptionthrown() {
        LoadStorageCheck storageUtils = new LoadStorageCheck("income");
        String newEntry = "-1|savings|2024-11-09||NONE|null|1";
        try {
            storageUtils.parseEntry(newEntry);
        } catch (WiagiStorageCorruptedException e) {
            assertEquals(storageUtils.storageErrorMessage + "amount", e.getMessage());
        }
    }

    @Test
    public void parseEntry_amountZero_storageCorruptedExceptionthrown() {
        LoadStorageCheck storageUtils = new LoadStorageCheck("income");
        String newEntry = "0|savings|2024-11-09||NONE|null|1";
        try {
            storageUtils.parseEntry(newEntry);
        } catch (WiagiStorageCorruptedException e) {
            assertEquals(storageUtils.storageErrorMessage + "amount", e.getMessage());
        }
    }

    @Test
    public void parseEntry_amountTooLarge_storageCorruptedExceptionthrown() {
        LoadStorageCheck storageUtils = new LoadStorageCheck("income");
        String newEntry = "10000001|savings|2024-11-09||NONE|null|1";
        try {
            storageUtils.parseEntry(newEntry);
        } catch (WiagiStorageCorruptedException e) {
            assertEquals(storageUtils.storageErrorMessage + "amount", e.getMessage());
        }
    }

    @Test
    public void parseEntry_nullDescription_storageCorruptedExceptionthrown() {
        LoadStorageCheck storageUtils = new LoadStorageCheck("income");
        String newEntry = "10.0|null|2024-11-09||NONE|null|1";
        try {
            storageUtils.parseEntry(newEntry);
        } catch (WiagiStorageCorruptedException e) {
            assertEquals(storageUtils.storageErrorMessage + "description!", e.getMessage());
        }
    }

    @Test
    public void parseEntry_emptyDescription_storageCorruptedExceptionthrown() {
        LoadStorageCheck storageUtils = new LoadStorageCheck("income");
        String newEntry = "10.0||2024-11-09||NONE|null|1";
        try {
            storageUtils.parseEntry(newEntry);
        } catch (WiagiStorageCorruptedException e) {
            assertEquals(storageUtils.storageErrorMessage + "description!", e.getMessage());
        }
    }

    @Test
    public void parseEntry_nullDate_storageCorruptedExceptionthrown() {
        LoadStorageCheck storageUtils = new LoadStorageCheck("income");
        String newEntry = "10.0|savings|null||NONE|null|1";
        try {
            storageUtils.parseEntry(newEntry);
        } catch (WiagiStorageCorruptedException e) {
            assertEquals(storageUtils.storageErrorMessage + "date!", e.getMessage());
        }
    }

    @Test
    public void parseEntry_invalidDate_storageCorruptedExceptionthrown() {
        LoadStorageCheck storageUtils = new LoadStorageCheck("income");
        String newEntry = "10.0|savings|2024-11-09||NONE|null|1";
        try {
            storageUtils.parseEntry(newEntry);
        } catch (WiagiStorageCorruptedException e) {
            assertEquals(storageUtils.storageErrorMessage + "date!", e.getMessage());
        }
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }
}