package seedu.type;

import seedu.Classes.Ui;
import seedu.exception.WiagiEmptyDescriptionException;

public class Spending {
    private int spendingAmount;
    private String spendingDescription;

    public Spending(String[] userInputWords, String userInput) {
        try {
            int spendingAmount = Integer.parseInt(userInputWords[2]);
            String spendingDescription = getSpendingDescription(spendingAmount, userInput);
            this.spendingAmount = spendingAmount;
            this.spendingDescription = spendingDescription;
            Ui.printWithTabNSeparator("Entry successfully added!");
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            Ui.printWithTabNSeparator("Did not enter a valid spending amount!");
        } catch (WiagiEmptyDescriptionException e) {
            Ui.printWithTabNSeparator(e.getMessage());
        }
    }

    private String getSpendingDescription(int spendingAmount, String userInput) throws WiagiEmptyDescriptionException {
        String[] spendingDescriptionWords = userInput.split(Integer.toString(spendingAmount));
        if (spendingDescriptionWords.length == 1) {
            throw new WiagiEmptyDescriptionException();
        }
        return spendingDescriptionWords[1].trim();
    }
}
