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

import static seedu.classes.Constants.DAILY_RECURRENCE;
import static seedu.classes.Constants.MONTHLY_RECURRENCE;
import static seedu.classes.Constants.YEARLY_RECURRENCE;

public class Type implements Serializable {
    private double amount;
    private String description;
    private LocalDate date;
    private String tag;
    private RecurrenceFrequency recurrenceFrequency;
    private LocalDate lastRecurrence;
    private int dayOfRecurrence;

    //@@author wongwh2002
    public Type(String userInput, double amount, String description) {
        this.amount = amount;
        assert amount > 0 : "Amount should be greater than zero";
        this.description = description;
        assert description != null && !description.isEmpty() : "Description should not be null or empty";
        this.date = extractDate(userInput);
        assert date != null : "Date should not be null";
        this.tag = extractTag(userInput);
        assert tag != null : "Tag should not be null";
        this.recurrenceFrequency = extractRecurrenceFrequency(userInput);
        this.lastRecurrence = checkRecurrence(this.recurrenceFrequency);
        if (lastRecurrence != null) {
            this.dayOfRecurrence = lastRecurrence.getDayOfMonth();
        }
        Ui.printWithTab("Entry successfully added!");
    }

    public Type(Type other) {
        this.amount = other.amount;
        this.description = other.description;
        this.date = other.date;
        this.tag = other.tag;
        this.recurrenceFrequency = RecurrenceFrequency.NONE;
        this.lastRecurrence = null;
        this.dayOfRecurrence = other.dayOfRecurrence;
    }

    public Type(int amount, String description, LocalDate date, String tag, RecurrenceFrequency recurrenceFrequency,
                LocalDate lastRecurrence, int dayOfRecurrence) {
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.tag = tag;
        this.recurrenceFrequency = recurrenceFrequency;
        this.lastRecurrence = lastRecurrence;
        this.dayOfRecurrence = dayOfRecurrence;
    }

    private String extractTag(String userInput) {
        String[] commandAndTag = userInput.split("\\*");
        if (commandAndTag.length == 1) {
            return "";
        }
        return commandAndTag[1].trim();
    }

    public double getAmount() {
        return this.amount;
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

    private RecurrenceFrequency extractRecurrenceFrequency(String userInput)
            throws WiagiInvalidInputException {
        String[] commandAndFrequency = userInput.split("~");
        if (commandAndFrequency.length == 1) {
            return RecurrenceFrequency.NONE;
        }
        String frequency = commandAndFrequency[1].toLowerCase();

        switch (frequency) {
        case DAILY_RECURRENCE:
            return RecurrenceFrequency.DAILY;
        case MONTHLY_RECURRENCE:
            return RecurrenceFrequency.MONTHLY;
        case YEARLY_RECURRENCE:
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
        String amountString = (amount % 1 == 0) ? String.valueOf((int) amount) : String.valueOf(amount);
        String returnString = description + Constants.LIST_SEPARATOR + amountString + Constants.LIST_SEPARATOR + date;
        if (!tag.isEmpty()) {
            returnString += Constants.LIST_SEPARATOR + tag;
        }
        return returnString;
    }

    public void editAmount(String newAmount) throws WiagiInvalidInputException{
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

    public void editDate(String date) throws WiagiInvalidInputException{
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

    public int getDayOfRecurrence() {
        return dayOfRecurrence;
    }
}
