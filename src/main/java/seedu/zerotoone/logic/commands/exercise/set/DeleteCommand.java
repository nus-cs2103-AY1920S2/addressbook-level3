package seedu.zerotoone.logic.commands.exercise.set;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.zerotoone.commons.core.Messages;

import seedu.zerotoone.commons.core.index.Index;
import seedu.zerotoone.logic.commands.CommandResult;
import seedu.zerotoone.logic.commands.exceptions.CommandException;
import seedu.zerotoone.model.Model;
import seedu.zerotoone.model.exercise.ExerciseSet;

/**
 * Deletes a exerciseSet identified using it's displayed index from the exerciseSet list.
 */
public class DeleteCommand extends SetCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the exerciseSet identified by the index number used in the displayed exerciseSet list.\n"
            + "Parameters: EXERCISESET_ID (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_EXERCISE_SET_SUCCESS = "Deleted ExerciseSet: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        // requireNonNull(model);
        // List<ExerciseSet> lastShownList = model.getFilteredExerciseSetList();

        // if (targetIndex.getZeroBased() >= lastShownList.size()) {
        //     throw new CommandException(Messages.MESSAGE_INVALID_EXERCISESET_DISPLAYED_INDEX);
        // }

        // ExerciseSet exerciseSetToDelete = lastShownList.get(targetIndex.getZeroBased());
        // model.deleteExerciseSet(exerciseSetToDelete);
        // return new CommandResult(String.format(MESSAGE_DELETE_EXERCISE_SET_SUCCESS, exerciseSetToDelete));
        return new CommandResult(MESSAGE_DELETE_EXERCISE_SET_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
