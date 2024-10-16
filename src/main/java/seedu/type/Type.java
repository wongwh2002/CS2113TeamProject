package seedu.type;

import seedu.classes.Constants;
import seedu.exception.WiagiEmptyDescriptionException;
import seedu.classes.Ui;
import seedu.exception.WiagiInvalidInputException;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Type implements Serializable {
    private int amount;
    private String description;
    private LocalDate date;

    public Type(String[] userInputWords, String userInput)
            throws WiagiEmptyDescriptionException, WiagiInvalidInputException {
        try {
            this.amount = Integer.parseInt(userInputWords[2]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new WiagiInvalidInputException("Did not enter a valid amount!");
        }
        this.description = getDescription(amount, userInput);
        this.date = getDate(userInput);
        Ui.printWithTab("Entry successfully added!");
    }

    public Type(int amount, String description, LocalDate date) {
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    public int getAmount() {
        return this.amount;
    }

    private String getDescription(int amount, String userInput) throws WiagiEmptyDescriptionException {
        String[] commandAndDescription = userInput.split(Integer.toString(amount), 2);
        if (commandAndDescription[1].isEmpty()) {
            throw new WiagiEmptyDescriptionException();
        }
        String[] descriptionAndDate = commandAndDescription[1].split("/", 2);
        return descriptionAndDate[0].trim();
    }

    private LocalDate getDate(String userInput) throws WiagiInvalidInputException {
        String[] commandAndDate = userInput.split("/", 2);
        try {
            if (commandAndDate.length == 1) {
                return LocalDate.now();
            }
            return LocalDate.parse(commandAndDate[1].trim());
        } catch (DateTimeParseException e) {
            throw new WiagiInvalidInputException("Invalid date format! Use \"/YYYY-MM-DD\"");
        }
    }

    public String toString() {
        return description + Constants.LIST_SEPARATOR + amount + Constants.LIST_SEPARATOR + date;
    }

    public void editAmount(String newAmount){
        try {
            this.amount = Integer.parseInt(newAmount);
        } catch (Exception e) {
            throw new WiagiInvalidInputException("Amount must be an integer");
        }
    }

    public void editDescription(String newDescription){
        this.description = newDescription;
    }

    public void editDate(String date) {
        try {
            this.date = LocalDate.parse(date);
        } catch (Exception e) {
            throw new WiagiInvalidInputException("Invalid date format! Use \"/YYYY-MM-DD\"");
        }
    }
}
