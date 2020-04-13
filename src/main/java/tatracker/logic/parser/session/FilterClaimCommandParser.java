//@@author Chuayijing
package tatracker.logic.parser.session;

import static tatracker.logic.parser.Prefixes.MODULE;

import tatracker.commons.core.Messages;
import tatracker.logic.commands.session.DoneSessionPredicate;
import tatracker.logic.commands.session.FilterClaimCommand;
import tatracker.logic.parser.ArgumentMultimap;
import tatracker.logic.parser.ArgumentTokenizer;
import tatracker.logic.parser.Parser;
import tatracker.logic.parser.exceptions.ParseException;

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

        if (!argMultimap.arePrefixesPresent(MODULE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(Messages.getInvalidCommandMessage(FilterClaimCommand.DETAILS.getUsage()));
        }

        String moduleCode = "";

        if (argMultimap.getValue(MODULE).isPresent()) {
            moduleCode = argMultimap.getValue(MODULE).map(String::trim).map(String::toUpperCase).get();
        }
        return new FilterClaimCommand(new DoneSessionPredicate(moduleCode));
    }
}
