package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditGroupCommand;
import seedu.address.logic.commands.EditGroupCommand.EditGroupDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
/**
 * Parses input arguments and creates a new EditGroupCommand instance.
 */
public class EditGroupCommandParser implements Parser<EditGroupCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditGroupCommand and returns an
     * EditGroupCommand instance for execution.
     * @param args
     * @return
     * @throws ParseException
     */
    public EditGroupCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_NAME, PREFIX_MEMBER);

        Index index;

        try {
            index = ParserUtil.parseIndex(argumentMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditGroupCommand.MESSAGE_USAGE), pe);
        }

        EditGroupDescriptor editGroupDescriptor = new EditGroupDescriptor();
        if (argumentMultimap.getValue(PREFIX_NAME).isPresent()) {
            editGroupDescriptor.setName(ParserUtil.parseName(argumentMultimap.getValue(PREFIX_NAME).get()));
        }
        parseIndexes(argumentMultimap, editGroupDescriptor);
        if (!editGroupDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditGroupCommand.MESSAGE_NOT_EDITED);
        }
        return new EditGroupCommand(index, editGroupDescriptor);
    }

    /**
     * Parses the member indexes if present and modifies the Edit Group Descriptor accordingly.
     * @param argumentMultimap
     * @param editGroupDescriptor
     * @throws ParseException
     */
    private void parseIndexes(ArgumentMultimap argumentMultimap,
                              EditGroupDescriptor editGroupDescriptor) throws ParseException {
        if (arePrefixesPresent(argumentMultimap, PREFIX_MEMBER)) {
            List<String> members = argumentMultimap.getAllValues(PREFIX_MEMBER);
            try {
                List<Integer> memberIDs = members.stream()
                        .map(s -> Integer.valueOf(s))
                        .collect(Collectors.toList());
                ArrayList<Integer> memberIds = new ArrayList<>(memberIDs);
                editGroupDescriptor.setMemberIds(memberIds);
            } catch (NumberFormatException e) {
                throw new ParseException("Member indexes provided must be an integer.");
            }

        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given {@code
     * ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
