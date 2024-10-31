package seedu.recurrence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.classes.Parser;
import seedu.commands.Command;
import seedu.type.Income;
import seedu.type.IncomeList;
import seedu.type.Spending;
import seedu.type.SpendingList;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.classes.Constants.VALID_TEST_DATE;

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
        String userInput = "list";
        Command c = Parser.parseUserInput(userInput);
        c.execute(incomes, spendings);
        assertEquals("\tSpendings" + System.lineSeparator() +
                        "\t1. food - 10 - " + VALID_TEST_DATE.minusMonths(1) + " - Recurring: MONTHLY"
                        + System.lineSeparator() + "\t2. food - 10 - " + VALID_TEST_DATE.minusMonths(1).plusMonths(1)
                        + System.lineSeparator() + "\tTotal spendings: 20" + System.lineSeparator() +
                        "\tIncomes" + System.lineSeparator() +
                        "\tTotal incomes: 0" + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    public void checkIncomeRecurrence_addRecurringPastMonthlyEntry_incomeListUpdated() {
        incomes.add(new Income(1000, "salary", VALID_TEST_DATE.minusMonths(1), "", RecurrenceFrequency.MONTHLY,
                VALID_TEST_DATE.minusMonths(1), VALID_TEST_DATE.minusMonths(1).getDayOfMonth()));
        incomes.updateRecurrence();
        String userInput = "list";
        Command c = Parser.parseUserInput(userInput);
        c.execute(incomes, spendings);
        assertEquals("\tSpendings" + System.lineSeparator() +
                        "\tTotal spendings: 0" + System.lineSeparator() +
                        "\tIncomes" + System.lineSeparator() +
                        "\t1. salary - 1000 - " + VALID_TEST_DATE.minusMonths(1) +
                        " - Recurring: MONTHLY" + System.lineSeparator() + "\t2. salary - 1000 - " +
                        VALID_TEST_DATE.minusMonths(1).plusMonths(1) + System.lineSeparator()
                        + "\tTotal incomes: 2000" + System.lineSeparator(),
                outContent.toString());
    }
}

