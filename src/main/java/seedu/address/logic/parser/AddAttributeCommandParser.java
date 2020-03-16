package seedu.address.logic.parser;

import seedu.address.logic.commands.AddAttributeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new object of type AddAttributeCommand
 */
public class AddAttributeCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the AddAttributeCommand
     * and returns an AddAttributeCommand object for execution.
     *
     * @param arguments the arguments to be parsed
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddAttributeCommand parse(String arguments) {
        return new AddAttributeCommand(arguments.trim());
    }
}
