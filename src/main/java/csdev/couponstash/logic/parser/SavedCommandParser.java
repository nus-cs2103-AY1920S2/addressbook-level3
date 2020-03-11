package csdev.couponstash.logic.parser;

import csdev.couponstash.logic.commands.SavedCommand;
import csdev.couponstash.logic.parser.exceptions.ParseException;

/**
 * This class contains logic for parsing a
 * given String into a SavedCommand that
 * can be executed by Coupon Stash
 */
public class SavedCommandParser implements Parser<SavedCommand> {
    /**
     * Parses a String (which will be taken from the
     * application when a user types in a command) into
     * the corresponding SavedCommand that it represents
     * @param userInput The String representing raw command
     *                  from the user.
     * @return Returns the corresponding SavedCommand,
     *     if parsing is successful.
     * @throws ParseException If the user's input does
     *     not match the exact format for the "saved"
     *     command (e.g. wrong prefix for the date)
     */
    @Override
    public SavedCommand parse(String userInput) throws ParseException {
        return new SavedCommand();
    }
}
