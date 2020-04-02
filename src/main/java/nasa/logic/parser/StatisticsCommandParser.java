package nasa.logic.parser;

import java.util.stream.Stream;

import nasa.logic.commands.StatisticsCommand;
import nasa.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates an Statistics object.
 */
public class StatisticsCommandParser implements Parser<StatisticsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the StatisticsCommand
     * and returns an StatisticsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public StatisticsCommand parse(String args) throws ParseException {
        return new StatisticsCommand();
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
