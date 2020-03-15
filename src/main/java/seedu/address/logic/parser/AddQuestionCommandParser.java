package seedu.address.logic.parser;

import seedu.address.logic.commands.AddQuestionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new object of type AddQuestionCommand
 */
public class AddQuestionCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the AddIntervieweeCommand
     * and returns an AddIntervieweeCommand object for execution.
     *
     * @param arguments the arguments to be parsed
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddQuestionCommand parse(String arguments) {
        return new AddQuestionCommand(arguments.trim());
    }

}
