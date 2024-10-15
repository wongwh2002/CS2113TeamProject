package seedu.type;

import seedu.classes.Constants;
import seedu.exception.WiagiEmptyDescriptionException;
import seedu.classes.Ui;
import seedu.exception.WiagiInvalidInputException;

import java.io.Serializable;

public class Type implements Serializable {
    private final int amount;
    private final String description;

    public Type(String[] userInputWords, String userInput)
            throws WiagiEmptyDescriptionException, WiagiInvalidInputException {
        try {
            this.amount = Integer.parseInt(userInputWords[2]);
            this.description = getDescription(amount, userInput);
            Ui.printWithTab("Entry successfully added!");
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new WiagiInvalidInputException("Did not enter a valid amount!");
        }
    }

    public Type(int amount, String description) {
        this.amount = amount;
        this.description = description;
    }

    public int getAmount() {
        return this.amount;
    }

    private String getDescription(int amount, String userInput) throws WiagiEmptyDescriptionException {
        String[] descriptionWords = userInput.split(Integer.toString(amount));
        if (descriptionWords.length == 1) {
            throw new WiagiEmptyDescriptionException();
        }
        return descriptionWords[1].trim();
    }

    public String toString() {
        return description + Constants.LIST_SEPARATOR + amount;
    }
}
