package seedu.zerotoone.logic.commands.exercise;

import static java.util.Objects.requireNonNull;
import static seedu.zerotoone.model.Model.PREDICATE_SHOW_ALL_EXERCISES;

import java.util.List;
import java.util.Optional;

import seedu.zerotoone.commons.core.Messages;

import seedu.zerotoone.commons.core.index.Index;
import seedu.zerotoone.commons.util.CollectionUtil;
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
    private final EditExerciseDescriptor editExerciseDescriptor;

    /**
     * @param exerciseId of the exercise in the filtered exercise list to edit
     * @param editExerciseDescriptor details to edit the exercise with
     */
    public EditCommand(Index exerciseId, EditExerciseDescriptor editExerciseDescriptor) {
        requireNonNull(exerciseId);
        requireNonNull(editExerciseDescriptor);

        this.exerciseId = exerciseId;
        this.editExerciseDescriptor = new EditExerciseDescriptor(editExerciseDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Exercise> lastShownList = model.getFilteredExerciseList();

        if (exerciseId.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
        }

        Exercise exerciseToEdit = lastShownList.get(exerciseId.getZeroBased());
        Exercise editedExercise = createEditedExercise(exerciseToEdit, editExerciseDescriptor);

        if (!exerciseToEdit.isSameExercise(editedExercise) && model.hasExercise(editedExercise)) {
            throw new CommandException(MESSAGE_DUPLICATE_EXERCISE);
        }

        model.setExercise(exerciseToEdit, editedExercise);
        model.updateFilteredExerciseList(PREDICATE_SHOW_ALL_EXERCISES);
        return new CommandResult(String.format(MESSAGE_EDIT_EXERCISE_SUCCESS, editedExercise));
    }

    /**
     * Creates and returns an {@code Exercise} with the details of {@code exerciseToEdit}
     * edited with {@code editExerciseDescriptor}.
     */
    private static Exercise createEditedExercise(Exercise exerciseToEdit,
            EditExerciseDescriptor editExerciseDescriptor) {
        assert exerciseToEdit != null;

        ExerciseName updatedExerciseName = editExerciseDescriptor.getExerciseName()
                .orElse(exerciseToEdit.getExerciseName());
        List<ExerciseSet> updatedExerciseSets = exerciseToEdit.getExerciseSets();

        return new Exercise(updatedExerciseName, updatedExerciseSets);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand otherCommand= (EditCommand) other;
        return exerciseId.equals(otherCommand.exerciseId)
                && editExerciseDescriptor.equals(otherCommand.editExerciseDescriptor);
    }

    /**
     * Stores the details to edit the exercise with. Each non-empty field value will replace the
     * corresponding field value of the exercise.
     */
    public static class EditExerciseDescriptor {
        private ExerciseName exerciseName;

        public EditExerciseDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditExerciseDescriptor(EditExerciseDescriptor toCopy) {
            setExerciseName(toCopy.exerciseName);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(exerciseName);
        }

        public void setExerciseName(ExerciseName exerciseName) {
            this.exerciseName = exerciseName;
        }

        public Optional<ExerciseName> getExerciseName() {
            return Optional.ofNullable(exerciseName);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            } else if (!(other instanceof EditExerciseDescriptor)) {
                return false;
            }

            EditExerciseDescriptor otherDescriptor = (EditExerciseDescriptor) other;
            return getExerciseName().equals(otherDescriptor.getExerciseName());
        }
    }
}
