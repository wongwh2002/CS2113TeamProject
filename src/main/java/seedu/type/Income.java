package seedu.type;

import seedu.exception.WiagiEmptyDescriptionException;

public class Income {
    private int incomeAmount;
    private String incomeDescription;

    private String getIncomeDescription(int incomeAmount, String userInput) throws WiagiEmptyDescriptionException {
        String[] incomeDescriptionWords = userInput.split(Integer.toString(incomeAmount));
        if (incomeDescriptionWords.length == 1) {
            throw new WiagiEmptyDescriptionException();
        }
        return incomeDescriptionWords[1].trim();
    }

    public Income(String[] userInputWords, String userInput) {
        try {
            int incomeAmount = Integer.parseInt(userInputWords[2]);
            String incomeDescription = getIncomeDescription(incomeAmount, userInput);
            this.incomeAmount = incomeAmount;
            this.incomeDescription = incomeDescription;
            System.out.println("Entry successfully added!");
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Did not enter a valid income amount!");
        } catch (WiagiEmptyDescriptionException e) {
            System.out.println(e);
        }
    }
}
