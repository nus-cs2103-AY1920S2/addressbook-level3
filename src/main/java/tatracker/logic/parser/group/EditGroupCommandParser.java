package tatracker.logic.parser.group;

import static tatracker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tatracker.logic.parser.Prefixes.GROUP;
import static tatracker.logic.parser.Prefixes.MODULE;
import static tatracker.logic.parser.Prefixes.NEWGROUP;
import static tatracker.logic.parser.Prefixes.NEWTYPE;

import java.util.stream.Stream;

import tatracker.logic.commands.group.EditGroupCommand;
import tatracker.logic.parser.ArgumentMultimap;
import tatracker.logic.parser.ArgumentTokenizer;
import tatracker.logic.parser.Parser;
import tatracker.logic.parser.ParserUtil;
import tatracker.logic.parser.Prefix;
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

        if (!arePrefixesPresent(argMultimap, GROUP, MODULE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditGroupCommand.DETAILS.getUsage()));
        }

        String groupCode = argMultimap.getValue(GROUP).get().toUpperCase();
        String moduleCode = argMultimap.getValue(MODULE).get().toUpperCase();

        Group group = new Group(groupCode);

        String newGroupCode;
        if (argMultimap.getValue(NEWGROUP).isPresent()) {
            newGroupCode = ParserUtil.parseValue(argMultimap.getValue(NEWGROUP).get());
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

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
