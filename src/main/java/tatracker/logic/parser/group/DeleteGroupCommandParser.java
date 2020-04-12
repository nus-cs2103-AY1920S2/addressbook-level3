//@@author aakanksha-rai

package tatracker.logic.parser.group;

import static tatracker.logic.parser.Prefixes.GROUP;
import static tatracker.logic.parser.Prefixes.MODULE;

import tatracker.commons.core.Messages;
import tatracker.logic.commands.group.DeleteGroupCommand;
import tatracker.logic.parser.ArgumentMultimap;
import tatracker.logic.parser.ArgumentTokenizer;
import tatracker.logic.parser.Parser;
import tatracker.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteGroupCommand object
 */
public class DeleteGroupCommandParser implements Parser<DeleteGroupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteGroupCommand
     * and returns an DeleteGroupCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteGroupCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, GROUP, MODULE);

        if (!argMultimap.arePrefixesPresent(GROUP, MODULE)
                 || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(Messages.getInvalidCommandMessage(DeleteGroupCommand.DETAILS.getUsage()));
        }

        String groupCode = argMultimap.getValue(GROUP).get().toUpperCase();
        String moduleCode = argMultimap.getValue(MODULE).get().toUpperCase();

        return new DeleteGroupCommand(groupCode, moduleCode);
    }
}
