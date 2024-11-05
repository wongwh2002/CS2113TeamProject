package seedu.recurrence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.classes.Parser;
import seedu.classes.Ui;
import seedu.commands.Command;
import seedu.type.Income;
import seedu.type.IncomeList;
import seedu.type.Spending;
import seedu.type.SpendingList;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.classes.Constants.VALID_TEST_DATE;

public class YearlyRecurrenceTest {
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
    public void checkSpendingRecurrence_addRecurringPastYearlyEntry_spendingListUpdated() {
        spendings.add(new Spending(10, "food", VALID_TEST_DATE.minusYears(1), "", RecurrenceFrequency.YEARLY,
                VALID_TEST_DATE.minusYears(1), VALID_TEST_DATE.minusYears(1).getDayOfMonth()));
        spendings.updateRecurrence();
        String userInput = "list";
        Command c = Parser.parseUserInput(userInput);
        c.execute(incomes, spendings);
        assertEquals("\tSpendings" + System.lineSeparator() +
                        "\t1. food - 10 - " + VALID_TEST_DATE.minusYears(1) + " - Recurring: YEARLY"
                        + System.lineSeparator() + "\t2. food - 10 - " + VALID_TEST_DATE + System.lineSeparator() +
                        "\tTotal spendings: 20" + System.lineSeparator() +
                        "\tIncomes" + System.lineSeparator() +
                        "\tTotal incomes: 0" + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    public void checkIncomeRecurrence_addRecurringPastYearlyEntry_incomeListUpdated() {
        incomes.add(new Income(100000, "salary", VALID_TEST_DATE.minusYears(1), "", RecurrenceFrequency.YEARLY,
                VALID_TEST_DATE.minusYears(1), VALID_TEST_DATE.minusYears(1).getDayOfMonth()));
        incomes.updateRecurrence();
        String userInput = "list";
        Command c = Parser.parseUserInput(userInput);
        c.execute(incomes, spendings);
        assertEquals("\tSpendings" + System.lineSeparator() +
                        "\tTotal spendings: 0" + System.lineSeparator() +
                        "\tIncomes" + System.lineSeparator() +
                        "\t1. salary - 100000 - " + VALID_TEST_DATE.minusYears(1) +
                        " - Recurring: YEARLY" + System.lineSeparator() + "\t2. salary - 100000 - " + VALID_TEST_DATE
                        + System.lineSeparator() + "\tTotal incomes: 200000" + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    public void checkSpendingRecurrence_addPastYearlyEntryNoBacklogging_noEntryAdded() {
        spendings.add(new Spending(10, "food", VALID_TEST_DATE.minusYears(1), "", RecurrenceFrequency.YEARLY,
                VALID_TEST_DATE.minusYears(1), VALID_TEST_DATE.minusYears(1).getDayOfMonth()));
        Ui.userInputForTest("n");
        Recurrence.checkRecurrenceBackLog(spendings.get(0), spendings);
        assertEquals(1, spendings.size());
    }

    @Test
    public void checkIncomeRecurrence_addPastYearlyEntryNoBacklogging_noEntryAdded() {
        incomes.add(new Income(100000, "salary", VALID_TEST_DATE.minusYears(1), "", RecurrenceFrequency.YEARLY,
                VALID_TEST_DATE.minusYears(1), VALID_TEST_DATE.minusYears(1).getDayOfMonth()));
        Ui.userInputForTest("n");
        Recurrence.checkRecurrenceBackLog(incomes.get(0), incomes);
        assertEquals(1, incomes.size());
    }
}

