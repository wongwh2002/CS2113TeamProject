package seedu.commands;

import seedu.exception.WiagiInvalidInputException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static seedu.classes.Constants.AMOUNT_NOT_NUMBER;
import static seedu.classes.Constants.INVALID_DATE_FORMAT;
import static seedu.classes.Constants.INVALID_AMOUNT;
import static seedu.classes.Constants.OVER_MAX_ENTRY_AMOUNT;
import static seedu.classes.Constants.MAX_ENTRY_AMOUNT;

public class CommandUtils {

    /**
     * Formats an amount given in the form of a string to a double rounded to 2 decimal places, and
     * checks that the amount is within the valid range. Throws WiagiInvalidInputException if amount is in
     * the incorrect format, or if it is out of the valid range.
     * @param stringAmount  amount to format
     * @param commandFormat correct command format, will be printed if format of amount is incorrect
     * @return amount formatted as a double, rounded to 2 decimal places
     */
    public static double formatAmount(String stringAmount, String commandFormat) {
        double newAmount = roundAmount(stringAmount, commandFormat);
        if (newAmount <= 0) {
            throw new WiagiInvalidInputException(INVALID_AMOUNT + commandFormat);
        }
        if (newAmount > MAX_ENTRY_AMOUNT) {
            throw new WiagiInvalidInputException(OVER_MAX_ENTRY_AMOUNT);
        }
        return newAmount;
    }

    /**
     * Formats a date given in the form of a string and returns a LocalDate object. Throws
     * WiagiInvalidInputException if the date is in the incorrect format
     * @param stringDate    date given by user as a String
     * @param commandFormat correct command format, will be printed if date is in the wrong format
     * @return date as LocalDate object
     */
    public static LocalDate formatDate(String stringDate, String commandFormat) {
        try {
            return LocalDate.parse(stringDate);
        } catch (DateTimeParseException e) {
            throw new WiagiInvalidInputException(INVALID_DATE_FORMAT + commandFormat);
        }
    }

    /**
     * Formats an amount given in the form of a string to a double rounded to 2 decimal places. Throws
     * WiagiInvalidInputException if amount is in the incorrect format.
     * @param stringAmount  amount to format
     * @param commandFormat correct command format, will be printed if format of amount is incorrect
     * @return amount formatted as a double, rounded to 2 decimal places
     */
    public static double roundAmount(String stringAmount, String commandFormat) {
        try {
            double doubleAmount = Double.parseDouble(stringAmount);
            return Math.round(doubleAmount * 100.0) / 100.0; //round to 2dp
        } catch (NumberFormatException e) {
            throw new WiagiInvalidInputException(AMOUNT_NOT_NUMBER + commandFormat);
        }
    }
}
