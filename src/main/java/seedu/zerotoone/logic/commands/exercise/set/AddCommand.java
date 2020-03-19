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
 * Edits the details of an existing exercise in the address book.
 */
public class AddCommand extends SetCommand {
    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = "Usage: exercise set add EXERCISE_ID r/<num_reps> m/<weight>";
    public static final String MESSAGE_EDIT_EXERCISE_SUCCESS = "Added exercise set: %1$s";

    private final Index exerciseId;
    private final ExerciseSet exerciseSet;

    /**
     * @param index of the exercise in the filtered exercise list to edit
     * @param exerciseSet details to edit the exercise with
     */
    public AddCommand(Index exerciseId, ExerciseSet exerciseSet) {
        requireNonNull(exerciseId);
        requireNonNull(exerciseSet);

        this.exerciseId = exerciseId;
        this.exerciseSet = exerciseSet;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Exercise> lastShownList = model.getFilteredExerciseList();

        if (exerciseId.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
        }

        Exercise exerciseToEdit = lastShownList.get(exerciseId.getZeroBased());
        Exercise editedExercise = createEditedExercise(exerciseToEdit, exerciseSet);

        model.setExercise(exerciseToEdit, editedExercise);
        model.updateFilteredExerciseList(PREDICATE_SHOW_ALL_EXERCISES);
        return new CommandResult(String.format(MESSAGE_EDIT_EXERCISE_SUCCESS, editedExercise));
    }

    /**
     * Creates and returns an {@code Exercise} with the details of {@code exerciseToEdit}
     * edited with {@code editExerciseDescriptor}.
     */
    private static Exercise createEditedExercise(Exercise exerciseToEdit, ExerciseSet exerciseSet) {
        assert exerciseToEdit != null;

        ExerciseName updatedExerciseName = exerciseToEdit.getExerciseName();
        List<ExerciseSet> updatedExerciseSets = new ArrayList<>(exerciseToEdit.getExerciseSets());
        updatedExerciseSets.add(exerciseSet);

        return new Exercise(updatedExerciseName, updatedExerciseSets);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (!(other instanceof AddCommand)) {
            return false;
        }

        // state check
        AddCommand otherCommand= (AddCommand) other;
        return exerciseId.equals(otherCommand.exerciseId)
                && exerciseSet.equals(otherCommand.exerciseSet);
    }
}
