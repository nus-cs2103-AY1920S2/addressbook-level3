package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.CourseManager;
import seedu.address.model.ModuleManager;
import seedu.address.model.ProfileManager;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    /**
     * Executes the command and returns the result message.
     *
     * @param profileManager {@code ProfileManager}, CourseManager {@code courseManager},
     *                                             ModuleManager {@code moduleManager}
     *                                             which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If user supplies an invalid Course, CourseFocusArea or ModuleCode
     */
    public abstract CommandResult execute(ProfileManager profileManager, CourseManager courseManager,
                                          ModuleManager moduleManager) throws CommandException;
}
