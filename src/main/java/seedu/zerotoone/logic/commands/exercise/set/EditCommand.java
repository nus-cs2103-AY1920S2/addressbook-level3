package seedu.zerotoone.logic.commands.exercise.set;

import static java.util.Objects.requireNonNull;
import static seedu.zerotoone.model.Model.PREDICATE_SHOW_ALL_EXERCISES;

import java.util.ArrayList;
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
import seedu.zerotoone.model.exercise.NumReps;
import seedu.zerotoone.model.exercise.Weight;

/**
 * Edits the details of an existing exercise in the address book.
 */
public class EditCommand extends SetCommand {
    public static final String COMMAND_WORD = "edit";
    public static final String MESSAGE_USAGE = "Usage: exercise set edit EXERCISE_ID SET_ID r/<num_reps> m/<weight>";
    public static final String MESSAGE_EDIT_EXERCISE_SUCCESS = "Edited exercise set: %1$s";

    private final Index exerciseId;
    private final Index setId;
    private final EditExerciseSetDescriptor editExerciseSetDescriptor;

    /**
     * @param index of the exercise in the filtered exercise list to edit
     * @param editExerciseSetDescriptor details to edit the exercise with
     */
    public EditCommand(Index exerciseId, Index setId, EditExerciseSetDescriptor editExerciseSetDescriptor) {
        requireNonNull(exerciseId);
        requireNonNull(setId);
        requireNonNull(editExerciseSetDescriptor);

        this.exerciseId = exerciseId;
        this.setId = setId;
        this.editExerciseSetDescriptor = new EditExerciseSetDescriptor(editExerciseSetDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Exercise> lastShownList = model.getFilteredExerciseList();

        if (exerciseId.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
        }

        Exercise exerciseToEdit = lastShownList.get(exerciseId.getZeroBased());
        Exercise editedExercise = createEditedExercise(exerciseToEdit, setId, editExerciseSetDescriptor);

        model.setExercise(exerciseToEdit, editedExercise);
        model.updateFilteredExerciseList(PREDICATE_SHOW_ALL_EXERCISES);
        return new CommandResult(String.format(MESSAGE_EDIT_EXERCISE_SUCCESS, editedExercise));
    }

    /**
     * Creates and returns an {@code Exercise} with the details of {@code exerciseToEdit}
     * edited with {@code editExerciseDescriptor}.
     */
    private static Exercise createEditedExercise(Exercise exerciseToEdit,
            Index setId, EditExerciseSetDescriptor editExerciseDescriptor) {
        assert exerciseToEdit != null;

        ExerciseName updatedExerciseName = exerciseToEdit.getExerciseName();

        List<ExerciseSet> exerciseSets = exerciseToEdit.getExerciseSets();
        List<ExerciseSet> updatedExerciseSets = new ArrayList<>();
        for (int i = 0; i < exerciseSets.size(); i++) {
            ExerciseSet exerciseSet = exerciseSets.get(i);
            if (i == setId.getZeroBased()) {
                NumReps updatedNumReps = editExerciseDescriptor.getNumReps()
                        .orElse(exerciseSet.getNumReps());
                Weight updatedWeight = editExerciseDescriptor.getWeight()
                        .orElse(exerciseSet.getWeight());
                ExerciseSet updatedExerciseSet = new ExerciseSet(updatedWeight, updatedNumReps);
                updatedExerciseSets.add(updatedExerciseSet);
            } else {
                updatedExerciseSets.add(exerciseSet);
            }
        }

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
                && setId.equals(otherCommand.setId)
                && editExerciseSetDescriptor.equals(otherCommand.editExerciseSetDescriptor);
    }

    /**
     * Stores the details to edit the exercise with. Each non-empty field value will replace the
     * corresponding field value of the exercise.
     */
    public static class EditExerciseSetDescriptor {
        private NumReps numReps;
        private Weight weight;

        public EditExerciseSetDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditExerciseSetDescriptor(EditExerciseSetDescriptor toCopy) {
            setNumReps(toCopy.numReps);
            setWeight(toCopy.weight);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(numReps, weight);
        }

        public void setNumReps(NumReps numReps) {
            this.numReps = numReps;
        }

        public Optional<NumReps> getNumReps() {
            return Optional.ofNullable(numReps);
        }
        
        public void setWeight(Weight weight) {
            this.weight = weight;
        }

        public Optional<Weight> getWeight() {
            return Optional.ofNullable(weight);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            } else if (!(other instanceof EditExerciseSetDescriptor)) {
                return false;
            }

            EditExerciseSetDescriptor otherDescriptor = (EditExerciseSetDescriptor) other;
            return getNumReps().equals(otherDescriptor.getNumReps())
                    && getWeight().equals(otherDescriptor.getWeight());
        }
    }
}
