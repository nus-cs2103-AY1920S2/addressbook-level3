package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.*;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import seedu.address.logic.commands.*;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new object of type AddCommand
 */
public class AddCommandParser implements Parser<AddCommand> {

    private static final Pattern BASIC_ADD_COMMAND_FORMAT = Pattern.compile("(?<addCommandWord>\\S+)(?<addArguments>.+)");

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String arguments) throws ParseException {
        Matcher matcher = BASIC_ADD_COMMAND_FORMAT.matcher(arguments.trim());
        final String addCommandWord = matcher.group("addCommandWord");
        final String addArguments = matcher.group("addArguments");

        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        switch (addCommandWord) {
        case AddAttributeCommand.COMMAND_WORD:
            return new AddAttributeCommandParser().parse(addArguments);

        case AddIntervieweeCommand.COMMAND_WORD:
            return new AddIntervieweeCommandParser().parse(addArguments);

        case AddQuestionCommand.COMMAND_WORD:
            return new AddQuestionCommandParser().parse(addArguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
