//@@author aakanksha-rai

package tatracker.logic.parser.module;

import static tatracker.logic.parser.Prefixes.MODULE;
import static tatracker.logic.parser.Prefixes.NAME;

import tatracker.commons.core.Messages;
import tatracker.logic.commands.module.AddModuleCommand;
import tatracker.logic.parser.ArgumentMultimap;
import tatracker.logic.parser.ArgumentTokenizer;
import tatracker.logic.parser.Parser;
import tatracker.logic.parser.exceptions.ParseException;
import tatracker.model.module.Module;

/**
 * Parses input arguments and creates a new AddModuleCommand object
 */
public class AddModuleCommandParser implements Parser<AddModuleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddModuleCommand
     * and returns an AddModuleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddModuleCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, MODULE, NAME);

        if (!argMultimap.arePrefixesPresent(MODULE, NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(Messages.getInvalidCommandMessage(AddModuleCommand.DETAILS.getUsage()));
        }

        // No need to parse trimmed strings
        String moduleCode = argMultimap.getValue(MODULE).get().toUpperCase();
        String name = argMultimap.getValue(NAME).get();

        Module module = new Module(moduleCode, name);

        return new AddModuleCommand(module);
    }
}
