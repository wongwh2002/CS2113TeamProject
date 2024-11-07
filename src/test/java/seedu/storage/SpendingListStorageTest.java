package seedu.storage;

import org.junit.jupiter.api.Test;
import seedu.classes.Ui;
import seedu.recurrence.RecurrenceFrequency;
import seedu.type.Spending;
import seedu.type.SpendingList;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.classes.Constants.VALID_TEST_DATE;

public class SpendingListStorageTest {
    @Test
    public void save_existingList_success() {
        SpendingList spendings = new SpendingList();
        spendings.add(new Spending(10, "macs", VALID_TEST_DATE, "", RecurrenceFrequency.NONE, null, 1));
        SpendingListStorage.save(spendings);
        assertTrue(new File("./spendings.txt").exists());
    }

    @Test
    public void load_existingFile_success() {
        SpendingListStorage.load();
        assertEquals(Storage.spendings.size(), 1);
    }

    @Test
    public void load_emptySpendingsAndPasswordFile_noBudgetSet() {
        File spendingsFile = new File("./spendings.txt");
        File passwordFile = new File("./password.txt");
        try {
            spendingsFile.delete();
            spendingsFile.createNewFile();
            passwordFile.delete();
            passwordFile.createNewFile();
            Ui.userInputForTest(1 + System.lineSeparator() + 2 + System.lineSeparator() + 3);
            SpendingListStorage.load();
            assertEquals(0, Storage.spendings.getDailyBudget());
            assertEquals(0, Storage.spendings.getMonthlyBudget());
            assertEquals(0, Storage.spendings.getYearlyBudget());
        } catch (IOException e) {
            Ui.printWithTab("Error with load_emptyFile_newBudgetSet test");
        }
    }
}
