// @@author Clouddoggo

package seedu.jelphabot.logic.parser;

import static seedu.jelphabot.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.jelphabot.commons.core.index.Index;
import seedu.jelphabot.logic.commands.StartTimerCommand;
import seedu.jelphabot.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new StartTimerCommand object
 */
public class StartTimerCommandParser implements Parser<StartTimerCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of StartTimerCommand
     * and returns a StartTimerCommand object for execution.
     * @param args user arguments.
     * @return a StartTimerCommand object to be used for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public StartTimerCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new StartTimerCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StartTimerCommand.MESSAGE_USAGE), pe);
        }
    }
}
