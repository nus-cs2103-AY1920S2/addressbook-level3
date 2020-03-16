package seedu.eylah.commons.logic;

import seedu.eylah.diettracker.logic.commands.CommandResult;
import seedu.eylah.diettracker.logic.commands.exceptions.CommandException;
import seedu.eylah.diettracker.logic.parser.exceptions.ParseException;

public interface MainLogic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;
}
