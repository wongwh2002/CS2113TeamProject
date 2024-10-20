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
        this.amount = extractAmount(userInputWords);
        assert amount > 0 : "Amount should be greater than zero";
        this.description = extractDescription(amount, userInput);
        assert !description.isEmpty() : "Description should not be empty";
        this.date = extractDate(userInput);
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

    private int extractAmount(String[] userInputWords) {
        try {
            int amount = Integer.parseInt(userInputWords[2]);
            if (amount <= 0) {
                throw new WiagiInvalidInputException("Amount must be greater than zero!");
            }
            return amount;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new WiagiInvalidInputException("No amount and description provided!");
        } catch (NumberFormatException e) {
            throw new WiagiInvalidInputException("Amount must be an integer!");
        }
    }

    private String extractDescription(int amount, String userInput) throws WiagiEmptyDescriptionException {
        String[] commandAndDescription = userInput.split(Integer.toString(amount), 2);
        if (commandAndDescription[1].isEmpty()) {
            throw new WiagiEmptyDescriptionException();
        }
        String[] descriptionAndDate = commandAndDescription[1].split("/", 2);
        return descriptionAndDate[0].trim();
    }

    private LocalDate extractDate(String userInput) throws WiagiInvalidInputException {
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
            int amount = Integer.parseInt(newAmount);
            if (amount <= 0) {
                throw new WiagiInvalidInputException("Amount must be greater than zero!");
            }
            this.amount = amount;
        } catch (NumberFormatException e) {
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

    public LocalDate getDate() {
        return this.date;
    }
}
