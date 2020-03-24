package seedu.address.logic.parser;

import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

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
        if (argsArray[0].equals("")) {
            numberOfUndo = 1;
        } else {
            try {
                numberOfUndo = Integer.parseInt(argsArray[0]);
                if (numberOfUndo < 1) {
                    throw new ParseException(UndoCommand.MESSAGE_CONSTRAINTS);
                }
            } catch (NumberFormatException nfe) {
                throw new ParseException(UndoCommand.MESSAGE_CONSTRAINTS);
            }
        }
        return new UndoCommand(numberOfUndo);
    }

}
