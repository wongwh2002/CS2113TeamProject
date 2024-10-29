package seedu.commands;

import seedu.exception.WiagiInvalidInputException;

import static seedu.classes.Constants.INVALID_AMOUNT;
import static seedu.classes.Constants.AMOUNT_NOT_NUMBER;

public class CommandUtils {

    public static double formatAmount(String stringAmount, String commandFormat) {
        try {
            double doubleAmount = Double.parseDouble(stringAmount);
            if (doubleAmount <= 0) {
                throw new WiagiInvalidInputException(INVALID_AMOUNT + commandFormat);
            }
            return Math.round(doubleAmount * 100.0) / 100.0; //round to 2dp
        } catch (NumberFormatException nfe) {
            throw new WiagiInvalidInputException(AMOUNT_NOT_NUMBER + commandFormat);
        }
    }
}
