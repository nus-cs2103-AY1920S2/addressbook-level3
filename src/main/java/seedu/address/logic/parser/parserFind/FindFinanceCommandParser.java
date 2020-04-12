package seedu.address.logic.parser.parserFind;

import seedu.address.logic.commands.commandFind.FindFinanceCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.modelFinance.FinanceNameContainsKeywordsPredicate;

import java.util.Arrays;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindFinanceCommandParser implements Parser<FindFinanceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand and returns a
     * FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindFinanceCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindFinanceCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindFinanceCommand(
                new FinanceNameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
