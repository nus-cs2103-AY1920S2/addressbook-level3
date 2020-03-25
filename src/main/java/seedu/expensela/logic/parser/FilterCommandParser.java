package seedu.expensela.logic.parser;

import static seedu.expensela.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.expensela.logic.commands.FilterCommand;
import seedu.expensela.logic.commands.FindCommand;
import seedu.expensela.logic.parser.exceptions.ParseException;
import seedu.expensela.model.transaction.CategoryEqualsKeywordPredicate;
import seedu.expensela.model.transaction.DateEqualsKeywordPredicate;
import seedu.expensela.model.transaction.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        if (nameKeywords.length != 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        } else if (nameKeywords[0].equals("category")) {

            // sends the next word after "category" to see if it matches any transaction categories
            return new FilterCommand(new CategoryEqualsKeywordPredicate(Arrays.asList(nameKeywords[1])));
        } else if (nameKeywords[0].equals("date")) {

            // sends the next word after "date" to see if it matches any transaction dates
            return new FilterCommand(new DateEqualsKeywordPredicate(Arrays.asList(nameKeywords[1])));
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }
    }

}
