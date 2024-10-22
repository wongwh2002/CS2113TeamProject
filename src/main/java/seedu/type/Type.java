package seedu.type;

import seedu.classes.Constants;
import seedu.exception.WiagiEmptyDescriptionException;
import seedu.classes.Ui;
import seedu.exception.WiagiInvalidInputException;
import seedu.exception.WiagiMissingParamsException;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Type implements Serializable {
    private int amount;
    private String description;
    private LocalDate date;
    private String tag;

    public Type(String[] userInputWords, String userInput) throws WiagiEmptyDescriptionException,
            WiagiMissingParamsException, WiagiInvalidInputException {
        this.amount = extractAmount(userInputWords);
        assert amount > 0 : "Amount should be greater than zero";
        this.description = extractDescription(amount, userInput);
        assert !description.isEmpty() : "Description should not be empty";
        this.date = extractDate(userInput);
        assert date != null : "Date should not be null";
        this.tag = extractTag(userInput);
        Ui.printWithTab("Entry successfully added!");
    }

    public Type(int amount, String description, LocalDate date, String tag) {
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.tag = tag;
    }

    public Type(int amount, String description, LocalDate date) {
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.tag = "";
    }

    private String extractTag(String userInput) {
        String[] commandAndTag = userInput.split("\\*");
        if (commandAndTag.length == 1) {
            return "";
        }
        return commandAndTag[1].trim();
    }

    public int getAmount() {
        return this.amount;
    }

    private int extractAmount(String[] userInputWords) throws WiagiMissingParamsException, WiagiInvalidInputException {
        try {
            int amount = Integer.parseInt(userInputWords[2]);
            if (amount <= 0) {
                throw new WiagiInvalidInputException("Amount must be greater than zero!");
            }
            return amount;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new WiagiMissingParamsException("No amount and description provided!");
        } catch (NumberFormatException e) {
            throw new WiagiInvalidInputException("Amount must be an integer!");
        }
    }

    private String extractDescription(int amount, String userInput) throws WiagiEmptyDescriptionException {
        String[] commandAndDescription = userInput.split(Integer.toString(amount));
        if (commandAndDescription[1].isEmpty()) {
            throw new WiagiEmptyDescriptionException();
        }
        String[] descriptionAndDate = commandAndDescription[1].split("[/*]");
        return descriptionAndDate[0].trim();
    }

    private LocalDate extractDate(String userInput) throws WiagiInvalidInputException {
        String[] commandAndDate = userInput.split("/");
        try {
            if (commandAndDate.length == 1) {
                return LocalDate.now();
            }
            return LocalDate.parse(commandAndDate[1].trim());
        } catch (DateTimeParseException e) {
            throw new WiagiInvalidInputException("Invalid date format! Use \"/YYYY-MM-DD/\"");
        }
    }

    public String toString() {
        String returnString = description + Constants.LIST_SEPARATOR + amount + Constants.LIST_SEPARATOR + date;
        if (!tag.isEmpty()) {
            returnString += Constants.LIST_SEPARATOR + tag;
        }
        return returnString;
    }

    public void editAmount(String newAmount){
        try {
            int amount = Integer.parseInt(newAmount);
            if (amount <= 0) {
                throw new WiagiInvalidInputException("Amount must be greater than zero!");
            }
            this.amount = amount;
        } catch (NumberFormatException e) {
            throw new WiagiInvalidInputException("Amount must be an integer.");
        }
    }

    public void editDescription(String newDescription){
        this.description = newDescription;
    }

    public void editDate(String date) {
        try {
            this.date = LocalDate.parse(date);
        } catch (Exception e) {
            throw new WiagiInvalidInputException("Invalid date format! Use \"/YYYY-MM-DD/\"");
        }
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void editTag(String newTag) {
        this.tag = newTag;
    }

    public String getTag() {
        return this.tag;
    }
}
