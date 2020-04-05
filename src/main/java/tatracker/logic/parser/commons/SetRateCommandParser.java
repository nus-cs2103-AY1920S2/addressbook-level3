package tatracker.logic.parser.commons;

import java.util.stream.Stream;

import tatracker.logic.commands.commons.SetRateCommand;
import tatracker.logic.parser.ArgumentMultimap;
import tatracker.logic.parser.Parser;
import tatracker.logic.parser.ParserUtil;
import tatracker.logic.parser.Prefix;
import tatracker.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new SetRateCommand object
 */
public class SetRateCommandParser implements Parser<SetRateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SetRateCommand
     * and returns a SetRateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetRateCommand parse(String args) throws ParseException {
        int rate = ParserUtil.parseRate(args);
        System.out.println("return new SetRateCommand:" + rate);
        return new SetRateCommand(rate);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
