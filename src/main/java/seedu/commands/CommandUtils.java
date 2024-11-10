package seedu.commands;

import seedu.exception.WiagiInvalidInputException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static seedu.classes.Constants.AMOUNT_NOT_NUMBER;
import static seedu.classes.Constants.INCORRECT_DATE_FORMAT;
import static seedu.classes.Constants.INVALID_AMOUNT;
import static seedu.classes.Constants.INVALID_AMOUNT_MAX;
import static seedu.classes.Constants.MAX_ENTRY_AMOUNT;

public class CommandUtils {

    public static double formatAmount(String stringAmount, String commandFormat) {
        double newAmount = roundAmount(stringAmount, commandFormat);
        if (newAmount <= 0) {
            throw new WiagiInvalidInputException(INVALID_AMOUNT + commandFormat);
        }
        if (newAmount > MAX_ENTRY_AMOUNT) {
            throw new WiagiInvalidInputException(INVALID_AMOUNT_MAX);
        }
        return newAmount;
    }

    public static LocalDate formatDate(String stringDate, String commandFormat) {
        try {
            return LocalDate.parse(stringDate);
        } catch (DateTimeParseException e) {
            throw new WiagiInvalidInputException(INCORRECT_DATE_FORMAT + commandFormat);
        }
    }

    public static double roundAmount(String stringAmount, String commandFormat) {
        try {
            double doubleAmount = Double.parseDouble(stringAmount);
            return Math.round(doubleAmount * 100.0) / 100.0; //round to 2dp
        } catch (NumberFormatException e) {
            throw new WiagiInvalidInputException(AMOUNT_NOT_NUMBER + commandFormat);
        }
    }
}
