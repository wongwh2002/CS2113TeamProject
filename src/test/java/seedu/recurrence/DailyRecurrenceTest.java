package seedu.recurrence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.classes.Ui;
import seedu.type.Income;
import seedu.type.IncomeList;
import seedu.type.Spending;
import seedu.type.SpendingList;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.classes.Constants.NEXT_LINE;
import static seedu.classes.Constants.TAB;
import static seedu.classes.Constants.TODAY;
import static seedu.classes.Ui.commandInputForTest;

public class DailyRecurrenceTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    private IncomeList incomes = new IncomeList();
    private SpendingList spendings = new SpendingList();

    @BeforeEach
    public void setUp() {
        incomes = new IncomeList();
        spendings = new SpendingList();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restore() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void checkSpendingRecurrence_addRecurringPastDailyEntry_success() {
        spendings.add(new Spending(10, "food", TODAY.minusDays(1), "", RecurrenceFrequency.DAILY,
                TODAY.minusDays(1), TODAY.minusDays(1).getDayOfMonth()));
        spendings.updateRecurrence();
        commandInputForTest("list", incomes, spendings);
        assertEquals(TAB + "Spendings" + NEXT_LINE +
                TAB + "1. food - 10 - " + TODAY.minusDays(1) + " - Recurring: DAILY"
                + NEXT_LINE + TAB + "2. food - 10 - " + TODAY + NEXT_LINE +
                TAB + "Total spendings: 20" + NEXT_LINE +
                TAB + "Incomes" + NEXT_LINE +
                TAB + "Total incomes: 0" + NEXT_LINE,
                outContent.toString());
    }

    @Test
    public void checkIncomeRecurrence_addRecurringPastDailyEntry_incomeListUpdated() {
        incomes.add(new Income(10, "tip", TODAY.minusDays(1), "", RecurrenceFrequency.DAILY,
                TODAY.minusDays(1), TODAY.minusDays(1).getDayOfMonth()));
        incomes.updateRecurrence();
        commandInputForTest("list", incomes, spendings);
        assertEquals(TAB + "Spendings" + NEXT_LINE +
                TAB + "Total spendings: 0" + NEXT_LINE +
                TAB + "Incomes" + NEXT_LINE +
                TAB + "1. tip - 10 - " + TODAY.minusDays(1) + " - Recurring: DAILY" +
                NEXT_LINE + TAB + "2. tip - 10 - " + TODAY + NEXT_LINE +
                TAB + "Total incomes: 20" + NEXT_LINE,
                outContent.toString());
    }

    @Test
    public void checkSpendingRecurrence_addPastDailyEntryNoBacklogging_noEntryAdded() {
        spendings.add(new Spending(10, "food", TODAY.minusDays(1), "", RecurrenceFrequency.DAILY,
                TODAY.minusDays(1), TODAY.minusDays(1).getDayOfMonth()));
        Ui.userInputForTest("n");
        Recurrence.checkRecurrenceBackLog(spendings.get(0), spendings);
        assertEquals(1, spendings.size());
    }

    @Test
    public void checkIncomeRecurrence_addPastDailyEntryNoBacklogging_noEntryAdded() {
        incomes.add(new Income(10, "tip", TODAY.minusDays(1), "", RecurrenceFrequency.DAILY,
                TODAY.minusDays(1), TODAY.minusDays(1).getDayOfMonth()));
        Ui.userInputForTest("n");
        Recurrence.checkRecurrenceBackLog(incomes.get(0), incomes);
        assertEquals(1, incomes.size());
    }
}
