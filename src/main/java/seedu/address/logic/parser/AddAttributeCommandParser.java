package seedu.address.logic.parser;

import seedu.address.logic.commands.AddAttributeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class AddAttributeCommandParser {
    public AddAttributeCommand parse(String arguments) {
        return new AddAttributeCommand(arguments.trim());
    }

}
