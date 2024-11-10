package seedu.type;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpendingListTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    private final LocalDate currentDate = LocalDate.of(2024, 10, 15);
    private SpendingList spendings;

    private final int dailySpending = 10000;
    private final int monthlySpending = 11000;
    private final int yearlySpending = 111010;


    @BeforeEach
    public void setUp() {
        spendings = new SpendingList();
        spendings.add(new Spending(1, "overYearAgo", currentDate.minusYears(2), null, null, null, 0));
        spendings.add(new Spending(10, "onlyInYear", currentDate.minusMonths(2), null, null, null, 0));
        spendings.add(new Spending(100, "notInCalendarYear", currentDate.minusMonths(11), null, null, null, 0));
        spendings.add(new Spending(1000, "monthAndYear", currentDate.minusDays(2), null, null, null, 0));
        spendings.add(new Spending(10000, "inAll", currentDate, null, null, null, 0));
        spendings.add(new Spending(100000, "notInCalendarMonth",currentDate.minusDays(16), null, null, null, 0));

        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restore() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void getMonthlySpending_basicInputs_returnMonthlySpendings() {
        assertEquals(monthlySpending, spendings.getMonthlySpending(currentDate));
    }

    @Test
    public void getDailySpending_basicInputs_returnDailySpendings() {
        assertEquals(dailySpending, spendings.getDailySpending(currentDate));
    }

    @Test
    public void getYearlySpending_basicInputs_returnYearlySpendings() {
        assertEquals(yearlySpending, spendings.getYearlySpending(currentDate));
    }
}
