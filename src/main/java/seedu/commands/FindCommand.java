package seedu.commands;

import seedu.classes.Ui;
import seedu.exception.WiagiInvalidIndexException;
import seedu.exception.WiagiInvalidInputException;
import seedu.exception.WiagiMissingParamsException;
import seedu.type.IncomeList;
import seedu.type.SpendingList;
import seedu.type.EntryType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static seedu.classes.Constants.*;

public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    private static final int LIST_TYPE_INDEX = 1;
    private static final int FIELD_INDEX = 2;
    private static final int VALUE_TO_FIND_INDEX = 3;
    private static final int FIND_ARGUMENTS_LENGTH = 4;
    private static final String AMOUNT_FIELD = "amount";
    private static final String DESCRIPTION_FIELD = "description";
    private static final String DATE_FIELD = "date";

    private final String fullCommand;

    public FindCommand(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    /**
     * Execute editing with the given arguments
     *
     * @param incomes   list of incomes in the application
     * @param spendings list of spendings in the application
     */
    @Override
    public void execute(IncomeList incomes, SpendingList spendings) {
        assert incomes != null;
        assert spendings != null;
        try {
            handleCommand(incomes, spendings);
        } catch (WiagiMissingParamsException | WiagiInvalidInputException | WiagiInvalidIndexException e) {
            Ui.printWithTab(e.getMessage());
        }
    }

    private void handleCommand(IncomeList incomes, SpendingList spendings)
            throws WiagiMissingParamsException, WiagiInvalidIndexException {
        String[] arguments = extractArguments();
        String typeOfList = arguments[LIST_TYPE_INDEX];
        if (!(typeOfList.equals(SPENDING) || typeOfList.equals(INCOME))) {
            throw new WiagiInvalidInputException(INVALID_CATEGORY + FIND_COMMAND_FORMAT);
        }
        List<? extends EntryType> findResults;
        switch (typeOfList) {
        case INCOME:
            findResults = findList(arguments, incomes);
            Ui.printFindResults(findResults, incomes);
            break;
        case SPENDING:
            findResults = findList(arguments, spendings);
            Ui.printFindResults(findResults, spendings);
            break;
        default:
            throw new WiagiInvalidInputException(INVALID_CATEGORY + FIND_COMMAND_FORMAT);
        }
    }

    private String[] extractArguments() throws WiagiMissingParamsException {
        String[] arguments = fullCommand.split(SPACE_REGEX, FIND_ARGUMENTS_LENGTH);
        if (arguments.length < FIND_ARGUMENTS_LENGTH) {
            throw new WiagiMissingParamsException(INCORRECT_PARAMS_NUMBER + FIND_COMMAND_FORMAT);
        }
        return arguments;
    }

    private <T extends ArrayList<? extends EntryType>> List<? extends EntryType> findList(String[] arguments, T list) {
        String findValue = arguments[VALUE_TO_FIND_INDEX];
        String field = arguments[FIELD_INDEX];
        switch (field) {
        case AMOUNT_FIELD:
            double findAmount = CommandUtils.formatAmount(findValue, FIND_COMMAND_FORMAT);
            return list.stream()
                    .filter(entry -> entry.getAmount() == findAmount)
                    .toList();
        case DESCRIPTION_FIELD:
            String findDescription = findValue.trim();
            return list.stream()
                    .filter(entry -> entry.getDescription().contains(findDescription))
                    .toList();
        case DATE_FIELD:
            LocalDate findDate = CommandUtils.formatDate(findValue, FIND_COMMAND_FORMAT);
            return list.stream()
                    .filter(entry -> entry.getDate().equals(findDate))
                    .toList();
        default:
            throw new WiagiInvalidInputException(INVALID_FIELD + FIND_COMMAND_FORMAT);
        }
    }
}
