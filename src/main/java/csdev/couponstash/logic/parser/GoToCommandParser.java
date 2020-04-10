package csdev.couponstash.logic.parser;

import static csdev.couponstash.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_MONTH_YEAR;

import java.time.format.DateTimeParseException;

import csdev.couponstash.logic.commands.GoToCommand;
import csdev.couponstash.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new GoToCommand object
 */
public class GoToCommandParser implements Parser<GoToCommand> {

    private void throwParseException() throws ParseException {
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GoToCommand.MESSAGE_USAGE));
    }

    /**
     * Parses the given {@code String} of arguments in the context of the GoToCommand
     * and returns a GoToCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public GoToCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(args, PREFIX_MONTH_YEAR);
        boolean isMonthYearPresent = argMultiMap.getValue(PREFIX_MONTH_YEAR).isPresent();

        if (!isMonthYearPresent) {
            throwParseException();
        }

        String my = argMultiMap.getValue(PREFIX_MONTH_YEAR).get();

        try {
            ParserUtil.parseYearMonth(my);
        } catch (DateTimeParseException e) {
            throwParseException();
        }

        return new GoToCommand(my);
    }
}

