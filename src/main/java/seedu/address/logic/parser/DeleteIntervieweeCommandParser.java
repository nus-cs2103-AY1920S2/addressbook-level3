package seedu.address.logic.parser;

import seedu.address.logic.commands.DeleteIntervieweeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteIntervieweeCommand object
 */
public class DeleteIntervieweeCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteIntervieweeCommand
     * and returns a DeleteIntervieweeCommand object for execution.
     *
     * @param arguments the arguments to be parsed
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteIntervieweeCommand parse(String arguments) throws ParseException {
        return new DeleteIntervieweeCommand(arguments.trim());
    }
}
