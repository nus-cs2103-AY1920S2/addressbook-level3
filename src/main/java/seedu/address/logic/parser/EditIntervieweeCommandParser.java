package seedu.address.logic.parser;

import seedu.address.logic.commands.EditAttributeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditIntervieweeCommand object
 */
public class EditIntervieweeCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the EditIntervieweeCommand
     * and returns an EditIntervieweeCommand object for execution.
     *
     * @param arguments the arguments to be parsed
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditAttributeCommand parse(String arguments) throws ParseException {
        String oldName = ParserUtil.parseArgumentsBeforePrefix(arguments);
        String newName = ParserUtil.parseArgumentsAfterPrefix(arguments);
        return new EditAttributeCommand(oldName.trim(), newName.trim());
    }
}
