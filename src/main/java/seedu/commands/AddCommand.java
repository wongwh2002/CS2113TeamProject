package seedu.commands;

import seedu.classes.Ui;
import seedu.exception.WiagiEmptyDescriptionException;
import seedu.exception.WiagiInvalidInputException;
import seedu.exception.WiagiMissingParamsException;
import seedu.type.Income;
import seedu.type.IncomeList;
import seedu.type.Spending;
import seedu.type.SpendingList;

public class AddCommand extends Command {

    private final String fullCommand;
    public AddCommand(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    @Override
    public void execute(IncomeList incomes, SpendingList spendings) {
        String[] userInputWords = fullCommand.split(" ");
        try {
            if (userInputWords.length < 2) {
                throw new WiagiMissingParamsException("Missing Parameters");
            }
            if (userInputWords[1].equals("spending")) {
                addSpending(userInputWords, spendings);
            } else if (userInputWords[1].equals("income")) {
                addIncome(userInputWords, incomes);
            } else {
                throw new WiagiInvalidInputException("No such category to add");
            }
        } catch (WiagiInvalidInputException e) {
            Ui.printWithTab(e.getMessage());
            Ui.printWithTab("Invalid input. " +
                    "Please enter in the form: add [spending/income] [amount] [description] {/YYYY-MM-DD}");
        } catch (WiagiMissingParamsException e) {
            Ui.printWithTab(e.getMessage());
            Ui.printWithTab("An error occurred. Please try again.");
        }
    }

    private void addSpending(String[] userInputWords, SpendingList spendings) {
        assert userInputWords[1].equals("spending") : "command should be to add spending";
        try {
            Spending toAdd = new Spending(userInputWords, fullCommand);
            spendings.add(toAdd);
        } catch (WiagiInvalidInputException | WiagiMissingParamsException | WiagiEmptyDescriptionException e) {
            Ui.printWithTab(e.getMessage());
        }
    }

    private void addIncome(String[] userInputWords, IncomeList incomes) {
        assert userInputWords[1].equals("income") : "command should be to add income";
        try {
            Income toAdd = new Income(userInputWords, fullCommand);
            incomes.add(toAdd);
        } catch (WiagiInvalidInputException | WiagiMissingParamsException | WiagiEmptyDescriptionException e) {
            Ui.printWithTab(e.getMessage());
        }
    }
}
