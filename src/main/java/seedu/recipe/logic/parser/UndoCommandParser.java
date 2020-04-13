package seedu.recipe.logic.parser;

import seedu.recipe.commons.util.StringUtil;
import seedu.recipe.logic.commands.UndoCommand;
import seedu.recipe.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UndoCommand object
 */
public class UndoCommandParser implements Parser<UndoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UndoCommand
     * and returns an UndoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UndoCommand parse(String args) throws ParseException {
        String[] argsArray = args.trim().split(" ");
        if (argsArray.length > 1) {
            throw new ParseException(UndoCommand.MESSAGE_CONSTRAINTS);
        }

        int numberOfUndo;
        if (argsArray[0].isBlank()) {
            numberOfUndo = 1;
        } else if (argsArray[0].toLowerCase().equals("all")) {
            numberOfUndo = 0;
        } else {
            if (!StringUtil.isNonZeroUnsignedInteger(argsArray[0])) {
                throw new ParseException(UndoCommand.MESSAGE_CONSTRAINTS);
            }
            numberOfUndo = Integer.parseInt(argsArray[0]);
        }
        return new UndoCommand(numberOfUndo);
    }

}
