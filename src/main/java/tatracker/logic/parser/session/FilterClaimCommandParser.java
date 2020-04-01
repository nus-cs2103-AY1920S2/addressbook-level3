package tatracker.logic.parser.session;

import static tatracker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tatracker.logic.parser.Prefixes.MODULE;

import java.util.stream.Stream;

import tatracker.logic.commands.session.FilterClaimCommand;
import tatracker.logic.parser.ArgumentMultimap;
import tatracker.logic.parser.ArgumentTokenizer;
import tatracker.logic.parser.Parser;
import tatracker.logic.parser.ParserUtil;
import tatracker.logic.parser.Prefix;
import tatracker.logic.parser.exceptions.ParseException;
import tatracker.model.session.DoneSessionPredicate;

/**
 * Parses input arguments and creates a new FilterModuleCommand object
 */
public class FilterClaimCommandParser implements Parser<FilterClaimCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterSessionCommand
     * and returns a FilterSessionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterClaimCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, MODULE);

        if (!arePrefixesPresent(argMultimap, MODULE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                    FilterClaimCommand.DETAILS.getUsage()));
        }

        String moduleCode = "";

        if (argMultimap.getValue(MODULE).isPresent()) {
            moduleCode = ParserUtil.parseValue(argMultimap.getValue(MODULE).get()).toUpperCase();
        }
        return new FilterClaimCommand(new DoneSessionPredicate(moduleCode));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
