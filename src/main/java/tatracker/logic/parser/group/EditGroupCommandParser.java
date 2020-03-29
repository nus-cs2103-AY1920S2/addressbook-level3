package tatracker.logic.parser.group;

import static tatracker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tatracker.logic.parser.CliSyntax.PREFIX_GROUP;
import static tatracker.logic.parser.CliSyntax.PREFIX_MODULE;
import static tatracker.logic.parser.CliSyntax.PREFIX_NEWGROUP;
import static tatracker.logic.parser.CliSyntax.PREFIX_NEWTYPE;

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
import tatracker.model.module.Module;

/**
 * Parses input arguments and creates a new AddGroupCommand object
 */
public class EditGroupCommandParser implements Parser<EditGroupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddGroupCommand
     * and returns an AddGroupCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditGroupCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_GROUP, PREFIX_MODULE, PREFIX_NEWTYPE, PREFIX_NEWGROUP);

        if (!arePrefixesPresent(argMultimap, PREFIX_GROUP, PREFIX_MODULE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditGroupCommand.MESSAGE_USAGE));
        }

        String groupCode = argMultimap.getValue(PREFIX_GROUP).get();
        String moduleCode = argMultimap.getValue(PREFIX_MODULE).get();

        Group group = new Group(groupCode);
        Module module = new Module(moduleCode);

        String newGroupCode;
        if (argMultimap.getValue(PREFIX_NEWGROUP).isPresent()) {
            newGroupCode = ParserUtil.parseValue(argMultimap.getValue(PREFIX_NEWGROUP).get());
        } else {
            newGroupCode = groupCode;
        }

        GroupType newGroupType;
        if (argMultimap.getValue(PREFIX_NEWTYPE).isPresent()) {
            newGroupType = ParserUtil.parseGroupType(argMultimap.getValue(PREFIX_NEWTYPE).get());
        } else {
            newGroupType = null;
        }

        return new EditGroupCommand(group, module, newGroupCode, newGroupType);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
