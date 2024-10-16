package seedu.commands;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import seedu.classes.Parser;
import seedu.type.IncomeList;
import seedu.type.SpendingList;

import java.time.LocalDate;

public class AddCommandTest {
    SpendingList spendings = new SpendingList();
    IncomeList incomes = new IncomeList();

    @Test
    void addCommand_correctSpendingWithoutDateInput_success() {
        String userInput = "add spending 10 macs";
        LocalDate date = LocalDate.now();
        String expectedOutput = "macs - 10 - " + date;
        Command c = Parser.parse(userInput);
        c.execute(incomes, spendings);
        assertEquals(expectedOutput, spendings.get(0).toString());
    }

    @Test
    void addCommand_correctIncomeWithoutDateInput_success() {
        String userInput = "add income 1500 dishwasher";
        LocalDate date = LocalDate.now();
        String expectedOutput = "dishwasher - 1500 - " + date;
        Command c = Parser.parse(userInput);
        c.execute(incomes, spendings);
        assertEquals(expectedOutput, incomes.get(0).toString());
    }

    @Test
    void addCommand_correctSpendingWithDateInput_success() {
        String userInput = "add spending 10 macs /2024-10-10";
        Command c = Parser.parse(userInput);
        c.execute(incomes, spendings);
        assertEquals("macs - 10 - 2024-10-10", spendings.get(0).toString());
    }

    @Test
    void addCommand_correctIncomeWithDateInput_success() {
        String userInput = "add income 1500 dishwasher /2024-10-10";
        Command c = Parser.parse(userInput);
        c.execute(incomes, spendings);
        assertEquals("dishwasher - 1500 - 2024-10-10", incomes.get(0).toString());
    }

    @Test
    void addCommand_missingAmountSpendingInput_noSpendingAdded() {
        int initialSize = spendings.size();
        String userInput = "add spending macs";
        Command c = Parser.parse(userInput);
        c.execute(incomes, spendings);
        assertEquals(spendings.size(), initialSize);
    }

    @Test
    void addCommand_missingAmountIncomeInput_noIncomeAdded(){
        int initialSize = incomes.size();
        String userInput = "add income macs";
        Command c = Parser.parse(userInput);
        c.execute(incomes, spendings);
        assertEquals(incomes.size(), initialSize);
    }

    @Test
    void addCommand_missingDescriptionSpendingInput_noSpendingAdded(){
        int initialSize = spendings.size();
        String userInput = "add spending 1000";
        Command c = Parser.parse(userInput);
        c.execute(incomes, spendings);
        assertEquals(spendings.size(), initialSize);
    }

    @Test
    void addCommand_missingDescriptionIncomeInput_noIncomeAdded(){
        int initialSize = incomes.size();
        String userInput = "add income 1000";
        Command c = Parser.parse(userInput);
        c.execute(incomes, spendings);
        assertEquals(incomes.size(), initialSize);
    }

    @Test
    void addCommand_invalidDateInput_noIncomeAdded() {
        int initialSize = incomes.size();
        String userInput = "add income 1000 part-time /2024/10/10";
        Command c = Parser.parse(userInput);
        c.execute(incomes, spendings);
        assertEquals(incomes.size(), initialSize);
    }

    @Test
    void addCommand_invalidDateInput_noSpendingAdded() {
        int initialSize = spendings.size();
        String userInput = "add spending 1000 ipad /2024/10/10";
        Command c = Parser.parse(userInput);
        c.execute(incomes, spendings);
        assertEquals(spendings.size(), initialSize);
    }
}
