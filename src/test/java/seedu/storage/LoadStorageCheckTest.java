package seedu.storage;

import org.junit.jupiter.api.Test;
import seedu.exception.WiagiStorageCorruptedException;
import seedu.recurrence.RecurrenceFrequency;
import seedu.type.Income;
import seedu.type.Spending;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static seedu.classes.Constants.TODAY;

public class LoadStorageCheckTest {

    @Test
    public void parseEntry_validIncomeInput_success() {
        LoadStorageCheck storageUtils = new LoadStorageCheck("income");
        String newEntry = "10.0|savings|" + TODAY + "||NONE|null|1";
        Income income = (Income) storageUtils.parseEntry(newEntry);
        assertEquals(10.0, income.getAmount());
        assertEquals("savings", income.getDescription());
        assertEquals(TODAY, income.getDate());
        assertEquals("", income.getTag());
        assertEquals(RecurrenceFrequency.NONE, income.getRecurrenceFrequency());
        assertNull(income.getLastRecurrence());
        assertEquals(1, income.getDayOfRecurrence());
    }

    @Test
    public void parseEntry_validSpendingInput_success() {
        LoadStorageCheck storageUtils = new LoadStorageCheck("spending");
        String newEntry = "10.0|kfc|" + TODAY + "||NONE|null|1";
        Spending spending = (Spending) storageUtils.parseEntry(newEntry);
        assertEquals(10.0, spending.getAmount());
        assertEquals("kfc", spending.getDescription());
        assertEquals(TODAY, spending.getDate());
        assertEquals("", spending.getTag());
        assertEquals(RecurrenceFrequency.NONE, spending.getRecurrenceFrequency());
        assertNull(spending.getLastRecurrence());
        assertEquals(1, spending.getDayOfRecurrence());
    }

    @Test
    public void parseEntry_invalidEntryDataLength_storageCorruptedErrorMessage() {
        LoadStorageCheck storageUtils = new LoadStorageCheck("income");
        String newEntry = "10.0|savings|2024-11-09||NONE|null";
        try {
            storageUtils.parseEntry(newEntry);
        } catch (WiagiStorageCorruptedException e) {
            assertEquals(storageUtils.corruptedErrorMessage + LoadStorageCheck.STORAGE_COMPULSORY_SIZE,
                    e.getMessage());
        }
    }

    @Test
    public void parseEntry_amountNotNumber_amountStorageErrorMessage() {
        LoadStorageCheck storageUtils = new LoadStorageCheck("income");
        String newEntry = "10.0a|savings|2024-11-09||NONE|null|1";
        try {
            storageUtils.parseEntry(newEntry);
        } catch (WiagiStorageCorruptedException e) {
            assertEquals(storageUtils.storageErrorMessage + "amount", e.getMessage());
        }
    }

    @Test
    public void parseEntry_amountLessThanZero_amountStorageErrorMessage() {
        LoadStorageCheck storageUtils = new LoadStorageCheck("income");
        String newEntry = "-1|savings|2024-11-09||NONE|null|1";
        try {
            storageUtils.parseEntry(newEntry);
        } catch (WiagiStorageCorruptedException e) {
            assertEquals(storageUtils.storageErrorMessage + "amount", e.getMessage());
        }
    }

    @Test
    public void parseEntry_amountZero_amountStorageErrorMessage() {
        LoadStorageCheck storageUtils = new LoadStorageCheck("income");
        String newEntry = "0|savings|2024-11-09||NONE|null|1";
        try {
            storageUtils.parseEntry(newEntry);
        } catch (WiagiStorageCorruptedException e) {
            assertEquals(storageUtils.storageErrorMessage + "amount", e.getMessage());
        }
    }

    @Test
    public void parseEntry_amountTooLarge_amountStorageErrorMessage() {
        LoadStorageCheck storageUtils = new LoadStorageCheck("income");
        String newEntry = "10000001|savings|2024-11-09||NONE|null|1";
        try {
            storageUtils.parseEntry(newEntry);
        } catch (WiagiStorageCorruptedException e) {
            assertEquals(storageUtils.storageErrorMessage + "amount", e.getMessage());
        }
    }

    @Test
    public void parseEntry_nullDescription_descriptionStorageErrorMessage() {
        LoadStorageCheck storageUtils = new LoadStorageCheck("income");
        String newEntry = "10.0|null|2024-11-09||NONE|null|1";
        try {
            storageUtils.parseEntry(newEntry);
        } catch (WiagiStorageCorruptedException e) {
            assertEquals(storageUtils.storageErrorMessage + "description!", e.getMessage());
        }
    }

    @Test
    public void parseEntry_emptyDescription_descriptionStorageErrorMessage() {
        LoadStorageCheck storageUtils = new LoadStorageCheck("income");
        String newEntry = "10.0||2024-11-09||NONE|null|1";
        try {
            storageUtils.parseEntry(newEntry);
        } catch (WiagiStorageCorruptedException e) {
            assertEquals(storageUtils.storageErrorMessage + "description!", e.getMessage());
        }
    }

    @Test
    public void parseEntry_nullDate_dateStorageErrorMessage() {
        LoadStorageCheck storageUtils = new LoadStorageCheck("income");
        String newEntry = "10.0|savings|null||NONE|null|1";
        try {
            storageUtils.parseEntry(newEntry);
        } catch (WiagiStorageCorruptedException e) {
            assertEquals(storageUtils.storageErrorMessage + "date!", e.getMessage());
        }
    }

    @Test
    public void parseEntry_invalidDate_dateStorageErrorMessage() {
        LoadStorageCheck storageUtils = new LoadStorageCheck("income");
        String newEntry = "10.0|savings|2024-13-09||NONE|null|1";
        try {
            storageUtils.parseEntry(newEntry);
        } catch (WiagiStorageCorruptedException e) {
            assertEquals(storageUtils.storageErrorMessage + "date!", e.getMessage());
        }
    }

    @Test
    public void parseEntry_nullTag_tagStorageErrorMessage() {
        LoadStorageCheck storageUtils = new LoadStorageCheck("income");
        String newEntry = "10.0|savings|2024-11-09|null|NONE|null|1";
        try {
            storageUtils.parseEntry(newEntry);
        } catch (WiagiStorageCorruptedException e) {
            assertEquals(storageUtils.storageErrorMessage + "tag!", e.getMessage());
        }
    }

    @Test
    public void parseEntry_nullRecurrenceFrequency_recurrenceFrequencyStorageErrorMessage() {
        LoadStorageCheck storageUtils = new LoadStorageCheck("income");
        String newEntry = "10.0|savings|2024-11-09||null|null|1";
        try {
            storageUtils.parseEntry(newEntry);
        } catch (WiagiStorageCorruptedException e) {
            assertEquals(storageUtils.storageErrorMessage + LoadStorageCheck.STORAGE_RECURRENCE_FREQUENCY,
                    e.getMessage());
        }
    }

    @Test
    public void parseEntry_invalidRecurrenceFrequency_recurrenceFrequencyStorageErrorMessage() {
        LoadStorageCheck storageUtils = new LoadStorageCheck("income");
        String newEntry = "10.0|savings|2024-11-09||WEEKLY|null|1";
        //recurrence frequency should be NONE, DAILY, MONTHLY, YEARLY
        try {
            storageUtils.parseEntry(newEntry);
        } catch (WiagiStorageCorruptedException e) {
            assertEquals(storageUtils.storageErrorMessage + LoadStorageCheck.STORAGE_RECURRENCE_FREQUENCY,
                    e.getMessage());
        }
    }

    @Test
    public void parseEntry_nullLastRecurredDate_lastRecurredDateStorageErrorMessage() {
        LoadStorageCheck storageUtils = new LoadStorageCheck("income");
        String newEntry = "10.0|savings|2024-11-09||NONE|null|1";
        try {
            storageUtils.parseEntry(newEntry);
        } catch (WiagiStorageCorruptedException e) {
            assertEquals(storageUtils.storageErrorMessage + LoadStorageCheck.STORAGE_LAST_RECURRED_DATE,
                    e.getMessage());
        }
    }

    @Test
    public void parseEntry_invalidLastRecurredDate_lastRecurredDateStorageErrorMessage() {
        LoadStorageCheck storageUtils = new LoadStorageCheck("income");
        String newEntry = "10.0|savings|2024-11-09||DAILY|null|1";
        try {
            storageUtils.parseEntry(newEntry);
        } catch (WiagiStorageCorruptedException e) {
            assertEquals(storageUtils.storageErrorMessage + LoadStorageCheck.STORAGE_LAST_RECURRED_DATE,
                    e.getMessage());
        }
    }

    @Test
    public void parseEntry_nullDayOfRecurrence_dayRecurrenceStorageErrorMessage() {
        LoadStorageCheck storageUtils = new LoadStorageCheck("income");
        String newEntry = "10.0|savings|2024-11-09||NONE|null|null";
        try {
            storageUtils.parseEntry(newEntry);
        } catch (WiagiStorageCorruptedException e) {
            assertEquals(storageUtils.storageErrorMessage + LoadStorageCheck.STORAGE_DAY_RECURRENCE,
                    e.getMessage());
        }
    }

    @Test
    public void parseEntry_tooLargeDayOfRecurrence_validDayRecurrenceStorageErrorMessage() {
        LoadStorageCheck storageUtils = new LoadStorageCheck("income");
        String newEntry = "10.0|savings|2024-11-09||NONE|null|32";
        try {
            storageUtils.parseEntry(newEntry);
        } catch (WiagiStorageCorruptedException e) {
            assertEquals(storageUtils.storageErrorMessage + LoadStorageCheck.STORAGE_VALID_DAY_RECURRENCE,
                    e.getMessage());
        }
    }

    @Test
    public void parseEntry_tooSmallDayOfRecurrence_validDayRecurrenceStorageErrorMessage() {
        LoadStorageCheck storageUtils = new LoadStorageCheck("income");
        String newEntry = "10.0|savings|2024-11-09||NONE|null|0";
        try {
            storageUtils.parseEntry(newEntry);
        } catch (WiagiStorageCorruptedException e) {
            assertEquals(storageUtils.storageErrorMessage + LoadStorageCheck.STORAGE_VALID_DAY_RECURRENCE,
                    e.getMessage());
        }
    }
}
