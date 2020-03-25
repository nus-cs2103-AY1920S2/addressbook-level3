package seedu.recipe.logic.parser;

import static seedu.recipe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.recipe.commons.core.index.Index;
import seedu.recipe.logic.commands.UnfavouriteCommand;
import seedu.recipe.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UnfavouriteCommand object
 */
public class UnfavouriteCommandParser implements Parser<UnfavouriteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnfavouriteCommand
     * and returns a UnfavouriteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnfavouriteCommand parse(String args) throws ParseException {
        try {
            Index[] index = ParserUtil.parseMultipleIndex(args);
            return new UnfavouriteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnfavouriteCommand.MESSAGE_USAGE), pe);
        }
    }
}
