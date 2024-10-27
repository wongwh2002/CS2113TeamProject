package seedu.storage;

import org.junit.jupiter.api.Test;
import seedu.recurrence.RecurrenceFrequency;
import seedu.type.Spending;
import seedu.type.SpendingList;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.classes.Constants.VALID_TEST_DATE;

public class SpendingListStorageTest {
    @Test
    public void save_existingList_success() {
        SpendingList spendings = new SpendingList();
        spendings.add(new Spending(10, "macs", VALID_TEST_DATE, "", RecurrenceFrequency.NONE, null, 0));
        SpendingListStorage.save(spendings);
        assertTrue(new File("./spendings.txt").exists());
    }

    @Test
    public void load_existingFile_success() {
        SpendingListStorage.load();
        assertEquals(Storage.spendings.size(), 1);
    }
}
