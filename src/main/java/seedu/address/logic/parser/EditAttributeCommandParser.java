package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.EditAttributeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditAttributeCommand object
 */
public class EditAttributeCommandParser {

    private static final Pattern BASIC_EDIT_ATTRIBUTE_COMMAND_FORMAT =
            Pattern.compile("(?<oldAttribute>\\S+) (?<newAttribute>\\S+)");

    /**
     * Parses the given {@code String} of arguments in the context of the EditAttributeCommand
     * and returns an EditAttributeCommand object for execution.
     *
     * @param arguments the arguments to be parsed
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditAttributeCommand parse(String arguments) throws ParseException {
        Matcher matcher = BASIC_EDIT_ATTRIBUTE_COMMAND_FORMAT.matcher(arguments.trim());

        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditAttributeCommand.MESSAGE_USAGE));
        }
        final String oldAttribute = matcher.group("oldAttribute");
        final String newAttribute = matcher.group("newAttribute");
        return new EditAttributeCommand(oldAttribute.trim(), newAttribute.trim());
    }
}
