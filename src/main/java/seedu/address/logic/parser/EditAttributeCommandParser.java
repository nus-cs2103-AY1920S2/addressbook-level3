package seedu.address.logic.parser;

import seedu.address.logic.commands.EditAttributeCommand;
import seedu.address.logic.commands.EditQuestionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class EditAttributeCommandParser {

    private static final Pattern BASIC_EDIT_ATTRIBUTE_COMMAND_FORMAT = Pattern.compile("(?<oldAttribute>\\S+)(?<newAttribute>)");

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
