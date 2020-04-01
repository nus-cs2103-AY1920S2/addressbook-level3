package csdev.couponstash.logic.parser;

import static csdev.couponstash.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.format.DateTimeParseException;

import csdev.couponstash.logic.commands.GoToCommand;
import csdev.couponstash.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new GoToCommand object
 */
public class GoToCommandParser implements Parser<GoToCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GoToCommand
     * and returns a GoToCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public GoToCommand parse(String args) throws ParseException {
        String trimmedArg = args.trim();
        try {
            ParserUtil.parseYearMonth(trimmedArg);
            return new GoToCommand(trimmedArg);
        } catch (DateTimeParseException | ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GoToCommand.MESSAGE_USAGE), pe);
        }
    }
}

