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

    private final LocalDate currentDate = LocalDate.now();
    private SpendingList spendings;

    private final int dailySpending = 10000;
    private final int monthlySpending = 11000;
    private final int yearlySpending = 11110;


    @BeforeEach
    public void setUp() {
        spendings = new SpendingList();
        spendings.add(new Spending(1, "overAyearAgo", currentDate.minusYears(2)));
        spendings.add(new Spending(10, "onlyInYear", currentDate.minusMonths(2)));
        spendings.add(new Spending(100, "onlyInYear", currentDate.minusMonths(11)));
        spendings.add(new Spending(1000, "monthAndYear", currentDate.minusDays(2)));
        spendings.add(new Spending(10000, "inAll", currentDate));

        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restore() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void getMonthlySpending_basicInputs_shouldReturnMonthlySpendings() {
        assertEquals(monthlySpending, spendings.getMonthlySpending());
    }

    @Test
    public void getDailySpending_basicInputs_shouldReturnDailySpendings() {
        assertEquals(dailySpending, spendings.getDailySpending());
    }

    @Test
    public void getYearlySpending_basicInputs_shouldReturnYearlySpendings() {
        assertEquals(yearlySpending, spendings.getYearlySpending());
    }
}
