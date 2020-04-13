package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.Group;
import seedu.address.model.person.ActivityList;
import seedu.address.model.person.Name;
import seedu.address.model.person.PlaceList;
import seedu.address.model.person.TimeList;

/**
 * Parses the input argument to create a new AddGroupCommand instance
 */
public class AddGroupCommandParser implements Parser<AddGroupCommand> {

    public static final String MEMBER_NOT_INT = "Member index supplied must be an integer";

    /**
     * Parses the input args to create an AddGroupCommand instance.
     *
     * @param args input by user
     * @return a new AddGroupCommand instance
     * @throws ParseException when input doesn't match expected pattern/format.
     */
    public AddGroupCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_MEMBER);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGroupCommand.MESSAGE_USAGE));
        }
        PlaceList placeList = new PlaceList(new ArrayList<>());
        ActivityList activityList = new ActivityList(new ArrayList<>());
        TimeList timeList = new TimeList(new ArrayList<>());

        try {
            Group group = new Group(new Name(argMultimap.getValue(PREFIX_NAME).get()),
                    placeList, activityList, timeList);

            if (arePrefixesPresent(argMultimap, PREFIX_MEMBER)) {
                List<String> members = argMultimap.getAllValues(PREFIX_MEMBER);
                for (int i = 0; i < members.size(); i++) {
                    try {
                        group.addPerson(Integer.parseInt(members.get(i)));
                    } catch (NumberFormatException e) {
                        throw new ParseException(MEMBER_NOT_INT);
                    }
                }
            }
            return new AddGroupCommand(group);
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
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
