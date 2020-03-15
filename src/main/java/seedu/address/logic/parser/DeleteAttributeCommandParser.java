package seedu.address.logic.parser;

import seedu.address.logic.commands.DeleteAttributeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteAttributeCommand object
 */
public class DeleteAttributeCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteAttributeCommand
     * and returns a DeleteAttributeCommand object for execution.
     *
     * @param arguments the arguments to be parsed
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteAttributeCommand parse(String arguments) {
        return new DeleteAttributeCommand(arguments.trim());
    }
}
