package seedu.zerotoone.logic.commands.schedule;

import seedu.zerotoone.commons.core.index.Index;
import seedu.zerotoone.logic.commands.CommandResult;
import seedu.zerotoone.logic.commands.exceptions.CommandException;
import seedu.zerotoone.model.Model;
import seedu.zerotoone.model.schedule.DateTime;

/**
 * STEPH_TODO_JAVADOC
 */
public class CreateCommand extends ScheduleCommand {
    public static final String COMMAND_WORD = "create";
    public static final String MESSAGE_USAGE = "Usage: schedule create WORKOUT_ID d/<dateTime>";
    public static final String MESSAGE_SUCCESS = "New schedule added: %1$s";
    public static final String MESSAGE_DUPLICATE_EXERCISE = "This schedule already exists";

    /**
     * Creates a CreateCommand to add the specified {@code Schedule}
     */
    public CreateCommand(Index workoutId, DateTime dateTIme) {
        // STEPH_TODO_JAVADOC
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        String outputMessage = String.format(MESSAGE_SUCCESS, "scheduled workout [testing]");
        return new CommandResult(outputMessage);
    }

    @Override
    public boolean equals(Object other) {
        return other == this; // short circuit if same object
    }
}
