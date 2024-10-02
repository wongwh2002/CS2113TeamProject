package seedu.commands;

import seedu.classes.Ui;
import seedu.type.Income;
import seedu.type.IncomeList;
import seedu.type.Spending;
import seedu.type.SpendingList;

public class AddCommand extends Command {
    private final String userInput;

    public AddCommand(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public void execute(IncomeList incomes, SpendingList spendings) {
        String[] userInputWords = userInput.split(" ");
        try {
            if (userInputWords.length < 2) {
                throw new IllegalArgumentException();
            }
            if (userInputWords[1].equals("spending")) {
                spendings.add(new Spending(userInputWords, userInput));
            } else if (userInputWords[1].equals("income")) {
                incomes.add(new Income(userInputWords, userInput));
            } else {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            Ui.printWithTab("Invalid input. " +
                    "Please enter in the form: add [spending/income] [amount] [description]...");
        } catch (Exception e) {
            Ui.printWithTab("An error occurred. Please try again.");
        }
    }
}
