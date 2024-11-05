package seedu.commands;

import seedu.exception.WiagiInvalidInputException;

import java.time.LocalDate;

import static seedu.classes.Constants.AMOUNT_NOT_NUMBER;
import static seedu.classes.Constants.INCORRECT_DATE_FORMAT;
import static seedu.classes.Constants.INVALID_AMOUNT;
import static seedu.classes.Constants.INVALID_AMOUNT_ZERO;

public class CommandUtils {

    public static double formatAmount(String stringAmount, String commandFormat) {
        try {
            double doubleAmount = Double.parseDouble(stringAmount);
            if (doubleAmount <= 0) {
                throw new WiagiInvalidInputException(INVALID_AMOUNT + commandFormat);
            }
            double newAmount =  Math.round(doubleAmount * 100.0) / 100.0; //round to 2dp
            if (newAmount <= 0) {
                throw new WiagiInvalidInputException(INVALID_AMOUNT_ZERO + commandFormat);
            }
            return newAmount;
        } catch (NumberFormatException nfe) {
            throw new WiagiInvalidInputException(AMOUNT_NOT_NUMBER + commandFormat);
        }
    }

    public static LocalDate formatDate(String stringDate, String commandFormat) {
        try {
            return LocalDate.parse(stringDate);
        } catch (Exception e) {
            throw new WiagiInvalidInputException(INCORRECT_DATE_FORMAT + commandFormat);
        }
    }
}
