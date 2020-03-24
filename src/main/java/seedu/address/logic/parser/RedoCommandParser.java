package seedu.address.logic.parser;

import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RedoCommand object
 */
public class RedoCommandParser implements Parser<RedoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RedoCommand
     * and returns a RedoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RedoCommand parse(String args) throws ParseException {
        String[] argsArray = args.trim().split(" ");
        if (argsArray.length > 1) {
            throw new ParseException(RedoCommand.MESSAGE_CONSTRAINTS);
        }

        int numberOfRedo;
        if (argsArray[0].isBlank()) {
            numberOfRedo = 1;
        } else {
            try {
                numberOfRedo = Integer.parseInt(argsArray[0]);
                if (numberOfRedo < 1) {
                    throw new ParseException(RedoCommand.MESSAGE_CONSTRAINTS);
                }
            } catch (NumberFormatException nfe) {
                throw new ParseException(RedoCommand.MESSAGE_CONSTRAINTS);
            }
        }
        return new RedoCommand(numberOfRedo);
    }

}
