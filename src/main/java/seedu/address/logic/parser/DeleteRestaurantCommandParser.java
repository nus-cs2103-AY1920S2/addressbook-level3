package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteRestaurantCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteRestaurantCommandParser implements Parser<DeleteRestaurantCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteRestaurantCommand
     * and returns a DeleteRestaurantCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteRestaurantCommand parse(String args) throws ParseException {
        Index index;
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args);

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRestaurantCommand.MESSAGE_USAGE), pe);
        }

        return new DeleteRestaurantCommand(index);
    }
}
