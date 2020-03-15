package seedu.address.logic.parser;

import seedu.address.logic.commands.AddAttributeCommand;
import seedu.address.logic.commands.DeleteAttributeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class DeleteAttributeCommandParser {
    public DeleteAttributeCommand parse(String arguments)  {
        return new DeleteAttributeCommand(arguments.trim());
    }
}
