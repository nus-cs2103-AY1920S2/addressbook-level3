package seedu.foodiebot.logic.parser;

import static seedu.foodiebot.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.foodiebot.logic.parser.CliSyntax.PREFIX_DATE_BY_DAY;
import static seedu.foodiebot.logic.parser.CliSyntax.PREFIX_DATE_BY_MONTH;
import static seedu.foodiebot.logic.parser.CliSyntax.PREFIX_DATE_BY_WEEK;

import seedu.foodiebot.logic.commands.BudgetCommand;
import seedu.foodiebot.logic.parser.exceptions.ParseException;
import seedu.foodiebot.model.budget.Budget;

/** Parses input arguments and creates a new BudgetCommand object */
public class BudgetCommandParser implements Parser<BudgetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the BudgetCommand and returns a
     * BudgetCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public BudgetCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, BudgetCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        String action = nameKeywords[0];
        switch (action) {
        case "set":
            if (nameKeywords.length != 3) {
                break;
            }

            String duration = nameKeywords[1];
            if (!isValidDuration(duration)) {
                break;
            }

            float value;
            try {
                value = Float.parseFloat(nameKeywords[2]);
            } catch (Exception e) {
                break;
            }

            Budget budget = new Budget(value, duration);
            return new BudgetCommand(budget, action);

        case "view":
            // Budget budget = [load budget object from storage?]
            // return new BudgetCommand(budget);
            return new BudgetCommand(action);
            // break; // delete this later

        default:
            break;
        }

        throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, BudgetCommand.MESSAGE_USAGE));

    }

    private static boolean isValidDuration(String duration) {
        return duration.equals(PREFIX_DATE_BY_DAY.toString())
                || duration.equals(PREFIX_DATE_BY_WEEK.toString())
                || duration.equals(PREFIX_DATE_BY_MONTH.toString());
    }




}
