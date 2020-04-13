//@@author aakanksha-rai

package tatracker.logic.parser.module;

import static tatracker.logic.parser.Prefixes.MODULE;

import tatracker.commons.core.Messages;
import tatracker.logic.commands.module.DeleteModuleCommand;
import tatracker.logic.parser.ArgumentMultimap;
import tatracker.logic.parser.ArgumentTokenizer;
import tatracker.logic.parser.Parser;
import tatracker.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteModuleCommand object
 */
public class DeleteModuleCommandParser implements Parser<DeleteModuleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteModuleCommand
     * and returns an DeleteModuleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteModuleCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, MODULE);

        if (!argMultimap.arePrefixesPresent(MODULE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(Messages.getInvalidCommandMessage(DeleteModuleCommand.DETAILS.getUsage()));
        }

        String moduleCode = argMultimap.getValue(MODULE).get().toUpperCase();

        return new DeleteModuleCommand(moduleCode);
    }
}
