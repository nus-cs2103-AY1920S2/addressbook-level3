//@@author aakanksha-rai

package tatracker.logic.parser.group;

import static tatracker.logic.parser.Prefixes.GROUP;
import static tatracker.logic.parser.Prefixes.MODULE;
import static tatracker.logic.parser.Prefixes.NEWGROUP;
import static tatracker.logic.parser.Prefixes.NEWTYPE;

import tatracker.commons.core.Messages;
import tatracker.logic.commands.group.EditGroupCommand;
import tatracker.logic.parser.ArgumentMultimap;
import tatracker.logic.parser.ArgumentTokenizer;
import tatracker.logic.parser.Parser;
import tatracker.logic.parser.ParserUtil;
import tatracker.logic.parser.exceptions.ParseException;
import tatracker.model.group.Group;
import tatracker.model.group.GroupType;

/**
 * Parses input arguments and creates a new EditGroupCommand object
 */
public class EditGroupCommandParser implements Parser<EditGroupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditGroupCommand
     * and returns an EditGroupCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditGroupCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, GROUP, MODULE, NEWTYPE, NEWGROUP);

        if (!argMultimap.arePrefixesPresent(GROUP, MODULE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(Messages.getInvalidCommandMessage(EditGroupCommand.DETAILS.getUsage()));
        }

        String groupCode = argMultimap.getValue(GROUP).get().toUpperCase();
        String moduleCode = argMultimap.getValue(MODULE).get().toUpperCase();

        Group group = new Group(groupCode);

        String newGroupCode;
        if (argMultimap.getValue(NEWGROUP).isPresent()) {
            newGroupCode = argMultimap.getValue(NEWGROUP).map(String::trim).map(String::toUpperCase).get();
        } else {
            newGroupCode = groupCode;
        }

        GroupType newGroupType;
        if (argMultimap.getValue(NEWTYPE).isPresent()) {
            newGroupType = ParserUtil.parseGroupType(argMultimap.getValue(NEWTYPE).get());
        } else {
            newGroupType = null;
        }

        return new EditGroupCommand(group, moduleCode, newGroupCode, newGroupType);
    }
}
