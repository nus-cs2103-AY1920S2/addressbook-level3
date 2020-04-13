package seedu.recipe.logic.parser;

import seedu.recipe.commons.util.StringUtil;
import seedu.recipe.logic.commands.RedoCommand;
import seedu.recipe.logic.commands.UndoCommand;
import seedu.recipe.logic.parser.exceptions.ParseException;

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
        } else if (argsArray[0].toLowerCase().equals("all")) {
            numberOfRedo = 0;
        } else {
            if (!StringUtil.isNonZeroUnsignedInteger(argsArray[0])) {
                throw new ParseException(UndoCommand.MESSAGE_CONSTRAINTS);
            }
            numberOfRedo = Integer.parseInt(argsArray[0]);
        }
        return new RedoCommand(numberOfRedo);
    }

}
