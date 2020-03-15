package seedu.address.logic.parser;

import seedu.address.logic.commands.AddAttributeCommand;
import seedu.address.logic.commands.AddQuestionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class AddQuestionCommandParser {
    public AddQuestionCommand parse(String arguments) throws ParseException {
        return new AddQuestionCommand(arguments.trim());
    }

}
