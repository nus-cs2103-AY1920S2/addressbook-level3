package seedu.zerotoone.logic.commands.exercise.set;

import static java.util.Objects.requireNonNull;
import static seedu.zerotoone.model.Model.PREDICATE_SHOW_ALL_EXERCISES;

import java.util.ArrayList;
import java.util.List;

import seedu.zerotoone.commons.core.Messages;

import seedu.zerotoone.commons.core.index.Index;
import seedu.zerotoone.logic.commands.CommandResult;
import seedu.zerotoone.logic.commands.exceptions.CommandException;
import seedu.zerotoone.model.Model;
import seedu.zerotoone.model.exercise.Exercise;
import seedu.zerotoone.model.exercise.ExerciseName;
import seedu.zerotoone.model.exercise.ExerciseSet;

/**
 * Deletes a exerciseSet identified using it's displayed index from the exerciseSet list.
 */
public class DeleteCommand extends SetCommand {
    public static final String COMMAND_WORD = "delete";
    public static final String MESSAGE_USAGE = "Usage: exercise set delete EXERCISE_ID SET_ID";
    public static final String MESSAGE_DELETE_EXERCISE_SET_SUCCESS = "Deleted Exercise Set: %1$s";

    private final Index exerciseId;
    private final Index setId;

    public DeleteCommand(Index exerciseId, Index setId) {
        requireNonNull(exerciseId);
        requireNonNull(setId);

        this.exerciseId = exerciseId;
        this.setId = setId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Exercise> lastShownList = model.getFilteredExerciseList();

        if (exerciseId.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
        }

        Exercise exerciseToEdit = lastShownList.get(exerciseId.getZeroBased());
        Exercise editedExercise = createEditedExercise(exerciseToEdit, setId);

        model.setExercise(exerciseToEdit, editedExercise);
        model.updateFilteredExerciseList(PREDICATE_SHOW_ALL_EXERCISES);
        return new CommandResult(String.format(MESSAGE_DELETE_EXERCISE_SET_SUCCESS, editedExercise));
    }

    /**
     * Creates and returns an {@code Exercise} with the details of {@code exerciseToEdit}
     * edited with {@code editExerciseDescriptor}.
     */
    private static Exercise createEditedExercise(Exercise exerciseToEdit, Index setId) {
        assert exerciseToEdit != null;

        ExerciseName updatedExerciseName = exerciseToEdit.getExerciseName();
        List<ExerciseSet> updatedExerciseSets = new ArrayList<>(exerciseToEdit.getExerciseSets());
        updatedExerciseSets.remove(setId.getZeroBased());

        return new Exercise(updatedExerciseName, updatedExerciseSets);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && exerciseId.equals(((DeleteCommand) other).exerciseId) // state check
                && setId.equals(((DeleteCommand) other).setId)); // state check
    }
}
