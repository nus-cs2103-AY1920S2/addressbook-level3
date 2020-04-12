package seedu.zerotoone.logic;

import seedu.zerotoone.logic.commands.CommandResult;
import seedu.zerotoone.logic.commands.exceptions.CommandException;
import seedu.zerotoone.logic.parser.exceptions.ParseException;
import seedu.zerotoone.ui.util.ViewType;

/**
 * API of the Logic component
 */
public interface Logic extends SessionLogic, ExerciseLogic,
        ScheduleLogic, WorkoutLogic, StatisticsLogic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Retrieves the view type of the command.
     * @param commandText The command as entered by the user.
     * @return the view type.
     * @throws ParseException If an error occurs during parsing.
     */
    ViewType getViewType(String commandText) throws ParseException;
}
