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
import static seedu.classes.Constants.TAB;
import static seedu.classes.Constants.VALID_TEST_DATE;
import static seedu.classes.Ui.commandInputForTest;

public class MonthlyRecurrenceTest {
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
    public void checkSpendingRecurrence_addRecurringPastMonthlyEntry_spendingListUpdated() {
        spendings.add(new Spending(10, "food", VALID_TEST_DATE.minusMonths(1), "", RecurrenceFrequency.MONTHLY,
                VALID_TEST_DATE.minusMonths(1), VALID_TEST_DATE.minusMonths(1).getDayOfMonth()));
        spendings.updateRecurrence();
        commandInputForTest("list", incomes, spendings);
        assertEquals(TAB + "Spendings" + System.lineSeparator() +
                TAB + "1. food - 10 - " + VALID_TEST_DATE.minusMonths(1) + " - Recurring: MONTHLY"
                + System.lineSeparator() + TAB + "2. food - 10 - " + VALID_TEST_DATE.minusMonths(1).plusMonths(1)
                + System.lineSeparator() + TAB + "Total spendings: 20" + System.lineSeparator() +
                TAB + "Incomes" + System.lineSeparator() +
                TAB + "Total incomes: 0" + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    public void checkIncomeRecurrence_addRecurringPastMonthlyEntry_incomeListUpdated() {
        incomes.add(new Income(1000, "salary", VALID_TEST_DATE.minusMonths(1), "", RecurrenceFrequency.MONTHLY,
                VALID_TEST_DATE.minusMonths(1), VALID_TEST_DATE.minusMonths(1).getDayOfMonth()));
        incomes.updateRecurrence();
        commandInputForTest("list", incomes, spendings);
        assertEquals(TAB + "Spendings" + System.lineSeparator() +
                TAB + "Total spendings: 0" + System.lineSeparator() +
                TAB + "Incomes" + System.lineSeparator() +
                TAB + "1. salary - 1000 - " + VALID_TEST_DATE.minusMonths(1) +
                " - Recurring: MONTHLY" + System.lineSeparator() + TAB + "2. salary - 1000 - " +
                VALID_TEST_DATE.minusMonths(1).plusMonths(1) + System.lineSeparator()
                + TAB + "Total incomes: 2000" + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    public void checkSpendingRecurrence_addPastMonthlyEntryNoBacklogging_noEntryAdded() {
        spendings.add(new Spending(10, "food", VALID_TEST_DATE.minusMonths(1), "", RecurrenceFrequency.MONTHLY,
                VALID_TEST_DATE.minusMonths(1), VALID_TEST_DATE.minusMonths(1).getDayOfMonth()));
        Ui.userInputForTest("n");
        Recurrence.checkRecurrenceBackLog(spendings.get(0), spendings);
        assertEquals(1, spendings.size());
    }

    @Test
    public void checkIncomeRecurrence_addPastMonthlyEntryNoBacklogging_noEntryAdded() {
        incomes.add(new Income(1000, "salary", VALID_TEST_DATE.minusMonths(1), "", RecurrenceFrequency.MONTHLY,
                VALID_TEST_DATE.minusMonths(1), VALID_TEST_DATE.minusMonths(1).getDayOfMonth()));
        Ui.userInputForTest("n");
        Recurrence.checkRecurrenceBackLog(incomes.get(0), incomes);
        assertEquals(1, incomes.size());
    }
}

