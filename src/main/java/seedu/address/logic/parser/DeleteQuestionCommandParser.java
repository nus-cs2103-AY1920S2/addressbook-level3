package seedu.address.logic.parser;

import seedu.address.logic.commands.DeleteQuestionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteQuestionCommand object
 */
public class DeleteQuestionCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteQuestionCommand
     * and returns a DeleteQuestionCommand object for execution.
     *
     * @param arguments the arguments to be parsed
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteQuestionCommand parse(String arguments) {
        return new DeleteQuestionCommand(arguments.trim());
    }
}
