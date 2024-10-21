package seedu.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.classes.Parser;
import seedu.type.Income;
import seedu.type.IncomeList;
import seedu.type.Spending;
import seedu.type.SpendingList;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.classes.Constants.VALID_TEST_DATE;

public class ListSpendingUserInputTest {
    private final LocalDate currentDate = LocalDate.now();
    private final IncomeList incomes = new IncomeList();
    private final SpendingList spendings = new SpendingList();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    @BeforeEach
    public void setUp() {
        spendings.add(new Spending(10, "girlfriends", VALID_TEST_DATE));
        spendings.add(new Spending(10, "macdonalds", VALID_TEST_DATE));
        incomes.add(new Income(10, "savings", VALID_TEST_DATE));
        incomes.add(new Income(10, "dividends", VALID_TEST_DATE));
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void execute_listSpendingWithStatistics_failureThenSuccess() {
        String userInout = "list spendings";
        Command c = Parser.parse(userInout);
        provideInput(String.format("g%sY%sN%s", System.lineSeparator(), System.lineSeparator(),
                System.lineSeparator()));
        c.execute(incomes, spendings);

        assertEquals("\tList all statistics? [Y/N]:" + System.lineSeparator() +
                        "\t____________________________________________________________" + System.lineSeparator() +
                        "\tPlease enter Y/N" + System.lineSeparator() +
                        "\tList all statistics? [Y/N]:" + System.lineSeparator() +
                        "\t____________________________________________________________" + System.lineSeparator() +
                        "\tSpendings" + System.lineSeparator() +
                        "\t1. girlfriends - 10 - " + currentDate + System.lineSeparator() +
                        "\t2. macdonalds - 10 - " + currentDate + System.lineSeparator() +
                        "\tTotal spendings: 20" + System.lineSeparator() +
                        "\t\tDaily spendings: 20" + System.lineSeparator() +
                        "\t\tDaily Budget: 0" + System.lineSeparator() +
                        "\t\tDaily budget left: -20" + System.lineSeparator() +
                        "\t\tMonthly spendings: 20" + System.lineSeparator() +
                        "\t\tMonthly Budget: 0" + System.lineSeparator() +
                        "\t\tMonthly budget left: -20" + System.lineSeparator() +
                        "\t\tYearly spendings: 20" + System.lineSeparator() +
                        "\t\tYearly Budget: 0" + System.lineSeparator() +
                        "\t\tYearly budget left: -20" + System.lineSeparator(),
                outContent.toString());
    }

    @Test
    public void execute_listSpendingWithoutStatistics_success() {
        String userInout = "list spendings";
        Command c = Parser.parse(userInout);
        c.execute(incomes, spendings);

        assertEquals("\tList all statistics? [Y/N]:" + System.lineSeparator() +
                        "\t____________________________________________________________" + System.lineSeparator() +
                        "\tSpendings" + System.lineSeparator() +
                        "\t1. girlfriends - 10 - " + currentDate + System.lineSeparator() +
                        "\t2. macdonalds - 10 - " + currentDate + System.lineSeparator() +
                        "\tTotal spendings: 20" + System.lineSeparator(),
                outContent.toString());
    }}
