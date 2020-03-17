package seedu.address.logic.commands.exercise;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUM_OF_REPS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUM_OF_SETS;

import java.util.Collections;
import java.util.HashSet;
// import java.util.List;
import java.util.Optional;
import java.util.Set;

// import seedu.address.commons.core.Messages;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.exercise.Exercise;
import seedu.address.model.exercise.ExerciseSet;
import seedu.address.model.exercise.Name;
import seedu.address.model.exercise.NumReps;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing exercise in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": creates a new exercise. "
            + "Parameters: "
            + PREFIX_EXERCISE_NAME + "NAME "
            + PREFIX_NUM_OF_REPS + "REPS "
            + PREFIX_NUM_OF_SETS + "SETS "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EXERCISE_NAME + "Pushups "
            + PREFIX_NUM_OF_REPS + "20 "
            + PREFIX_NUM_OF_SETS + "3 ";

    public static final String MESSAGE_EDIT_EXERCISE_SUCCESS = "Edited exercise: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_EXERCISE = "This exercise already exists.";

    private final Index index;
    private final EditExerciseDescriptor editExerciseDescriptor;

    /**
     * @param index of the exercise in the filtered exercise list to edit
     * @param editExerciseDescriptor details to edit the exercise with
     */
    public EditCommand(Index index, EditExerciseDescriptor editExerciseDescriptor) {
        requireNonNull(index);
        requireNonNull(editExerciseDescriptor);

        this.index = index;
        this.editExerciseDescriptor = new EditExerciseDescriptor(editExerciseDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        // List<Exercise> lastShownList = model.getFilteredExerciseList();

        // if (index.getZeroBased() >= lastShownList.size()) {
        //     throw new CommandException(Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
        // }

        // Exercise exerciseToEdit = lastShownList.get(index.getZeroBased());
        // Exercise editedExercise = createEditedExercise(exerciseToEdit, editExerciseDescriptor);

        // if (!exerciseToEdit.isSameExercise(editedExercise) && model.hasExercise(editedExercise)) {
        //     throw new CommandException(MESSAGE_DUPLICATE_EXERCISE);
        // }

        // model.setExercise(exerciseToEdit, editedExercise);
        // model.updateFilteredExerciseList(PREDICATE_SHOW_ALL_EXERCISES);
        // return new CommandResult(String.format(MESSAGE_EDIT_EXERCISE_SUCCESS, editedExercise));
        return new CommandResult(MESSAGE_EDIT_EXERCISE_SUCCESS);
    }

    /**
     * Creates and returns an {@code Exercise} with the details of {@code exerciseToEdit}
     * edited with {@code editExerciseDescriptor}.
     */
    private static Exercise createEditedExercise(Exercise exerciseToEdit,
            EditExerciseDescriptor editExerciseDescriptor) {
        assert exerciseToEdit != null;

        // Name updatedName = editExerciseDescriptor.getName().orElse(exerciseToEdit.getName());
        // NumReps updatedNumReps = editExerciseDescriptor
            // .getNumReps()
            // .orElse(exerciseToEdit.getNumReps());
        // ExerciseSet updatedExerciseSet = editExerciseDescriptor
            // .getExerciseSet()
            // .orElse(exerciseToEdit.getExerciseSet());
        // Set<Tag> updatedTags = editExerciseDescriptor.getTags().orElse(exerciseToEdit.getTags());

        Name updatedName = new Name("placeholder");
        ExerciseSet updatedExerciseSet = new ExerciseSet(null, null, null);

        return new Exercise(updatedName, updatedExerciseSet);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editExerciseDescriptor.equals(e.editExerciseDescriptor);
    }

    /**
     * Stores the details to edit the exercise with. Each non-empty field value will replace the
     * corresponding field value of the exercise.
     */
    public static class EditExerciseDescriptor {
        private Name name;
        private NumReps reps;
        private ExerciseSet sets;
        private Set<Tag> tags;

        public EditExerciseDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditExerciseDescriptor(EditExerciseDescriptor toCopy) {
            setName(toCopy.name);
            // setNumReps(gratoCopy.reps);
            setExerciseSet(toCopy.sets);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, reps, sets, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(NumReps reps) {
            this.reps = reps;
        }

        public Optional<NumReps> getPhone() {
            return Optional.ofNullable(reps);
        }

        public void setExerciseSet(ExerciseSet sets) {
            this.sets = sets;
        }

        public Optional<ExerciseSet> getEmail() {
            return Optional.ofNullable(sets);
        }

        /**
         * ExerciseSet {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditExerciseDescriptor)) {
                return false;
            }

            // state check
            EditExerciseDescriptor e = (EditExerciseDescriptor) other;

            // return getName().equals(e.getName())
            //         && getNumReps().equals(e.getNumReps())
            //         && getExerciseSet().equals(e.getExerciseSet())
            //         && getTags().equals(e.getTags());
            return true;
        }
    }
}
