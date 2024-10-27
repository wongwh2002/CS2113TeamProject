package seedu.storage;

import org.junit.jupiter.api.Test;
import seedu.recurrence.RecurrenceFrequency;
import seedu.type.Income;
import seedu.type.IncomeList;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.classes.Constants.VALID_TEST_DATE;

public class IncomeListStorageTest {
    @Test
    public void save_existingList_success() {
        IncomeList incomes = new IncomeList();
        incomes.add(new Income(10, "savings", VALID_TEST_DATE, "", RecurrenceFrequency.NONE, null, 0));
        IncomeListStorage.save(incomes);
        assertTrue(new File("./incomes.txt").exists());
    }

    @Test
    public void load_existingFile_success() {
        IncomeListStorage.load();
        assertEquals(Storage.incomes.size(), 1);
    }
}
