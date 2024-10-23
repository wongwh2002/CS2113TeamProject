package seedu.type;

import seedu.classes.Constants;
import seedu.exception.WiagiEmptyDescriptionException;
import seedu.classes.Ui;
import seedu.exception.WiagiInvalidInputException;
import seedu.exception.WiagiMissingParamsException;
import seedu.recurrence.RecurrenceFrequency;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Type implements Serializable {
    private int amount;
    private String description;
    private LocalDate date;
    private String tag;
    private RecurrenceFrequency recurrenceFrequency;
    private LocalDate lastRecurrence;

    public Type(String[] userInputWords, String userInput) throws WiagiEmptyDescriptionException,
            WiagiMissingParamsException, WiagiInvalidInputException {
        this.amount = extractAmount(userInputWords);
        assert amount > 0 : "Amount should be greater than zero";
        this.description = extractDescription(amount, userInput);
        assert description != null && !description.isEmpty() : "Description should not be null or empty";
        this.date = extractDate(userInput);
        assert date != null : "Date should not be null";
        this.tag = extractTag(userInput);
        assert tag != null : "Tag should not be null";
        this.recurrenceFrequency = extractRecurrenceFrequency(userInput);
        this.lastRecurrence = checkRecurrence(this.recurrenceFrequency);
        Ui.printWithTab("Entry successfully added!");
    }

    public Type(Type other) {
        this.amount = other.amount;
        this.description = other.description;
        this.date = other.date;
        this.tag = other.tag;
        this.recurrenceFrequency = RecurrenceFrequency.NONE;
        this.lastRecurrence = null;
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
            assert amount > 0 : "Amount should be greater than zero";
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

        String[] descriptionAndDate = commandAndDescription[1].trim().split(" ");
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

    private RecurrenceFrequency extractRecurrenceFrequency(String userInput) {
        String[] commandAndFrequency = userInput.split("~");
        if (commandAndFrequency.length == 1) {
            return RecurrenceFrequency.NONE;
        }
        String frequency = commandAndFrequency[1].toLowerCase();

        switch (frequency) {
        case "daily":
            return RecurrenceFrequency.DAILY;
        case "monthly":
            return RecurrenceFrequency.MONTHLY;
        case "yearly":
            return RecurrenceFrequency.YEARLY;
        default:
            throw new WiagiInvalidInputException("Invalid frequency type! Please input ~daily/monthly/yearly~");
        }
    }

    private LocalDate checkRecurrence(RecurrenceFrequency frequency) {
        if (frequency == RecurrenceFrequency.NONE) {
            return null;
        }
        return this.date;
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

    public void editDateWithLocalDate(LocalDate date) {
        this.date = date;
    }

    public void editLastRecurrence(LocalDate date) {
        this.lastRecurrence = date;
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

    public LocalDate getLastRecurrence() {
        return this.lastRecurrence;
    }

    public RecurrenceFrequency getRecurrenceFrequency() {
        return recurrenceFrequency;
    }
}
