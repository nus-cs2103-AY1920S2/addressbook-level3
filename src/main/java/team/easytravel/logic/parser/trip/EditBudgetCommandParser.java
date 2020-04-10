package team.easytravel.logic.parser.trip;

import team.easytravel.commons.core.Messages;
import team.easytravel.logic.commands.trip.EditBudgetCommand;
import team.easytravel.logic.parser.Parser;
import team.easytravel.logic.parser.exceptions.ParseException;
import team.easytravel.model.trip.Budget;

/**
 * Parses input arguments and creates a new EditBudgetCommand object
 */
public class EditBudgetCommandParser implements Parser<EditBudgetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditBudgetCommand
     * and returns an EditBudgetCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditBudgetCommand parse(String args) throws ParseException {
        String trimmedArg = args.trim();

        Integer amount;
        try {
            amount = Integer.parseInt(trimmedArg);
        } catch (NumberFormatException e) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    EditBudgetCommand.MESSAGE_USAGE));
        }

        if (!Budget.isValidBudget(amount)) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    Budget.MESSAGE_CONSTRAINTS));
        }

        try {
            Budget editedBudget = new Budget(amount);
            return new EditBudgetCommand(editedBudget);
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }
    }

}
