package seedu.recipe.logic.parser;

import static seedu.recipe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.recipe.logic.commands.QuoteCommand;
import seedu.recipe.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new quoteCommand object
 */
public class QuoteCommandParser implements Parser<QuoteCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the QuoteCommand
     * and returns a QuoteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public QuoteCommand parse(String args) throws ParseException {
        if (args.isBlank()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, QuoteCommand.MESSAGE_USAGE));
        } else {
            return new QuoteCommand(args);
        }
    }

}
