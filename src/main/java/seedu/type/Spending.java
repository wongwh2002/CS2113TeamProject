package seedu.type;

import seedu.exception.WiagiEmptyDescriptionException;

public class Spending {
    private int spendingAmount;
    private String spendingDescription;

    private String getSpendingDescription(int spendingAmount, String userInput) throws WiagiEmptyDescriptionException {
        String[] spendingDescriptionWords = userInput.split(Integer.toString(spendingAmount));
        if (spendingDescriptionWords.length == 1) {
            throw new WiagiEmptyDescriptionException();
        }
        return spendingDescriptionWords[1].trim();
    }

    public Spending(String[] userInputWords, String userInput) {
        try {
            int spendingAmount = Integer.parseInt(userInputWords[2]);
            String spendingDescription = getSpendingDescription(spendingAmount, userInput);
            this.spendingAmount = spendingAmount;
            this.spendingDescription = spendingDescription;
            System.out.println("Entry successfully added!");
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Did not enter a valid spending amount!");
        } catch (WiagiEmptyDescriptionException e) {
            System.out.println(e);
        }
    }
}
