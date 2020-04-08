package tatracker.logic.parser.module;

import static tatracker.logic.parser.Prefixes.MODULE;
import static tatracker.logic.parser.Prefixes.NAME;

import tatracker.commons.core.Messages;
import tatracker.logic.commands.module.EditModuleCommand;
import tatracker.logic.parser.ArgumentMultimap;
import tatracker.logic.parser.ArgumentTokenizer;
import tatracker.logic.parser.Parser;
import tatracker.logic.parser.ParserUtil;
import tatracker.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditModuleCommand object
 */
public class EditModuleCommandParser implements Parser<EditModuleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditModuleCommand
     * and returns an EditModuleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditModuleCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, MODULE, NAME);

        if (!argMultimap.arePrefixesPresent(MODULE, NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(Messages.getInvalidCommandMessage(EditModuleCommand.DETAILS.getUsage()));
        }

        String moduleCode = argMultimap.getValue(MODULE).get().toUpperCase();

        String newName = ParserUtil.parseValue(argMultimap.getValue(NAME).get());

        return new EditModuleCommand(moduleCode, newName);
    }
}
