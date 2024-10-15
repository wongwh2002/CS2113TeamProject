package seedu.type;

import seedu.classes.Constants;
import seedu.exception.WiagiEmptyDescriptionException;
import seedu.classes.Ui;
import seedu.exception.WiagiInvalidInputException;

public class Income {
    private int incomeAmount;
    private String incomeDescription;

    public Income(String[] userInputWords, String userInput) throws WiagiEmptyDescriptionException,
            WiagiInvalidInputException {
        try {
            int incomeAmount = Integer.parseInt(userInputWords[2]);
            String incomeDescription = getIncomeDescription(incomeAmount, userInput);
            this.incomeAmount = incomeAmount;
            this.incomeDescription = incomeDescription;
            Ui.printWithTab("Entry successfully added!");
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new WiagiInvalidInputException("Did not enter a valid income amount!");
        }
    }

    public Income(int incomeAmount, String incomeDescription) {
        this.incomeAmount = incomeAmount;
        this.incomeDescription = incomeDescription;
    }

    private String getIncomeDescription(int incomeAmount, String userInput) throws WiagiEmptyDescriptionException {
        String[] incomeDescriptionWords = userInput.split(Integer.toString(incomeAmount));
        if (incomeDescriptionWords.length == 1) {
            throw new WiagiEmptyDescriptionException();
        }
        return incomeDescriptionWords[1].trim();
    }

    public String toString() {
        return incomeDescription + Constants.LIST_SEPARATOR + incomeAmount;
    }
}
