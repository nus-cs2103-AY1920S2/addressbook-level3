package seedu.zerotoone.logic.commands.exercise;

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
public class EditCommand extends ExerciseCommand {
    public static final String COMMAND_WORD = "edit";
    public static final String MESSAGE_USAGE = "Usage: exercise edit EXERCISE_ID e/<exercise_name>";
    public static final String MESSAGE_EDIT_EXERCISE_SUCCESS = "Edited exercise: %1$s";
    public static final String MESSAGE_DUPLICATE_EXERCISE = "This exercise already exists.";

    private final Index exerciseId;
    private final ExerciseName exerciseName;

    /**
     * @param exerciseId of the exercise in the filtered exercise list to edit
     * @param exerciseName details to edit the exercise with
     */
    public EditCommand(Index exerciseId, ExerciseName exerciseName) {
        requireNonNull(exerciseId);
        requireNonNull(exerciseName);

        this.exerciseId = exerciseId;
        this.exerciseName = new ExerciseName(exerciseName.fullName);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Exercise> lastShownList = model.getFilteredExerciseList();
        if (exerciseId.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
        }

        Exercise exerciseToEdit = lastShownList.get(exerciseId.getZeroBased());
        ExerciseName updatedExerciseName;
        if (this.exerciseName != null) {
            updatedExerciseName = new ExerciseName(this.exerciseName.fullName);
        } else {
            updatedExerciseName = new ExerciseName(exerciseToEdit.getExerciseName().fullName);
        }
        List<ExerciseSet> updatedExerciseSets = new ArrayList<>(exerciseToEdit.getExerciseSets());
        Exercise editedExercise = new Exercise(updatedExerciseName, updatedExerciseSets);
        if (model.hasExercise(editedExercise)) {
            throw new CommandException(MESSAGE_DUPLICATE_EXERCISE);
        }

        model.setExercise(exerciseToEdit, editedExercise);
        model.updateFilteredExerciseList(PREDICATE_SHOW_ALL_EXERCISES);
        return new CommandResult(String.format(MESSAGE_EDIT_EXERCISE_SUCCESS, editedExercise));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand otherCommand = (EditCommand) other;
        return exerciseId.equals(otherCommand.exerciseId)
                && exerciseName.equals(otherCommand.exerciseName);
    }
}
