package seedu.type;

import seedu.exception.WiagiEmptyDescriptionException;
import seedu.Classes.Ui;

public class Income {
    private int incomeAmount;
    private String incomeDescription;

    public Income(String[] userInputWords, String userInput) {
        try {
            int incomeAmount = Integer.parseInt(userInputWords[2]);
            String incomeDescription = getIncomeDescription(incomeAmount, userInput);
            this.incomeAmount = incomeAmount;
            this.incomeDescription = incomeDescription;
            Ui.printWithTabNSeparator("Entry successfully added!");
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            Ui.printWithTabNSeparator("Did not enter a valid income amount!");
        } catch (WiagiEmptyDescriptionException e) {
            Ui.printWithTabNSeparator(e.getMessage());
        }
    }

    private String getIncomeDescription(int incomeAmount, String userInput) throws WiagiEmptyDescriptionException {
        String[] incomeDescriptionWords = userInput.split(Integer.toString(incomeAmount));
        if (incomeDescriptionWords.length == 1) {
            throw new WiagiEmptyDescriptionException();
        }
        return incomeDescriptionWords[1].trim();
    }
}
