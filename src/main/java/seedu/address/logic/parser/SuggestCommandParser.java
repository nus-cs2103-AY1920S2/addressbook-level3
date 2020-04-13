package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SuggestCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SuggestCommand object
 */
public class SuggestCommandParser implements Parser<SuggestCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SuggestCommand and returns a
     * SuggestCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SuggestCommand parse(String args) throws ParseException {
        try {
            String parameter = ParserUtil.parseSuggest(args);
            return new SuggestCommand(parameter);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SuggestCommand.MESSAGE_USAGE), pe);
        }
    }
}
