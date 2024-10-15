package seedu.commands;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import seedu.classes.Parser;
import seedu.type.IncomeList;
import seedu.type.SpendingList;

public class AddCommandTest {
    SpendingList spendings = new SpendingList();
    IncomeList incomes = new IncomeList();

    @Test
    void addCommand_correctSpendingInput_success() {
        String userInput = "add spending 10 macs";
        Command c = Parser.parse(userInput);
        c.execute(incomes, spendings);
        assertEquals("macs - 10", spendings.get(0).toString());
    }

    @Test
    void addCommand_correctIncomeInput_success() {
        String userInput = "add income 1500 dishwasher";
        Command c = Parser.parse(userInput);
        c.execute(incomes, spendings);
        assertEquals("dishwasher - 1500", incomes.get(0).toString());
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
}
