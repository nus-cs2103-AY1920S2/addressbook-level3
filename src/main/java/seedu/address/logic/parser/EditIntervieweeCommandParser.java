package seedu.address.logic.parser;

import seedu.address.logic.commands.EditAttributeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditIntervieweeCommandParser {

    public EditAttributeCommand parse(String arguments) throws ParseException {
        String oldName = ParserUtil.parseArgumentsBeforePrefix(arguments);
        String newName = ParserUtil.parseArgumentsAfterPrefix(arguments);
        return new EditAttributeCommand(oldName.trim(), newName.trim());
    }
}
