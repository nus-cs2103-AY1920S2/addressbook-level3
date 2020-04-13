//@@author aakanksha-rai

package tatracker.logic.parser.group;

import static tatracker.logic.parser.Prefixes.GROUP;
import static tatracker.logic.parser.Prefixes.MODULE;
import static tatracker.logic.parser.Prefixes.TYPE;

import tatracker.commons.core.Messages;
import tatracker.logic.commands.group.AddGroupCommand;
import tatracker.logic.parser.ArgumentMultimap;
import tatracker.logic.parser.ArgumentTokenizer;
import tatracker.logic.parser.Parser;
import tatracker.logic.parser.ParserUtil;
import tatracker.logic.parser.exceptions.ParseException;
import tatracker.model.group.Group;
import tatracker.model.group.GroupType;

/**
 * Parses input arguments and creates a new AddGroupCommand object
 */
public class AddGroupCommandParser implements Parser<AddGroupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddGroupCommand
     * and returns an AddGroupCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddGroupCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                         ArgumentTokenizer.tokenize(args, GROUP, MODULE, TYPE);

        if (!argMultimap.arePrefixesPresent(GROUP, MODULE, TYPE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(Messages.getInvalidCommandMessage(AddGroupCommand.DETAILS.getUsage()));
        }

        String groupCode = argMultimap.getValue(GROUP).get().toUpperCase();
        String moduleCode = argMultimap.getValue(MODULE).get().toUpperCase();
        GroupType groupType = ParserUtil.parseGroupType(argMultimap.getValue(TYPE).get());

        Group group = new Group(groupCode, groupType);

        return new AddGroupCommand(group, moduleCode);
    }
}
