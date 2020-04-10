package seedu.zerotoone.logic.commands.workout;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.zerotoone.testutil.Assert.assertThrows;
import static seedu.zerotoone.testutil.workout.TypicalWorkouts.ARMS_WORKOUT;
import static seedu.zerotoone.testutil.workout.TypicalWorkouts.LEGS_WORKOUT;
import static seedu.zerotoone.testutil.workout.WorkoutCommandTestUtil.VALID_WORKOUT_NAME_ABS_WORKOUT;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.zerotoone.logic.commands.CommandResult;
import seedu.zerotoone.logic.commands.exceptions.CommandException;
import seedu.zerotoone.model.workout.ReadOnlyWorkoutList;
import seedu.zerotoone.model.workout.Workout;
import seedu.zerotoone.model.workout.WorkoutList;
import seedu.zerotoone.model.workout.WorkoutName;
import seedu.zerotoone.testutil.ModelStub;
import seedu.zerotoone.testutil.workout.WorkoutBuilder;


public class CreateCommandTest {

    @Test
    public void constructor_nullWorkout_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CreateCommand(null));
    }

    @Test
    public void execute_workoutAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingWorkoutAdded modelStub = new ModelStubAcceptingWorkoutAdded();
        CommandResult commandResult = new CreateCommand(new WorkoutName(VALID_WORKOUT_NAME_ABS_WORKOUT))
                .execute(modelStub);
        Workout absWorkout = new WorkoutBuilder().withWorkoutName(VALID_WORKOUT_NAME_ABS_WORKOUT).build();

        assertEquals(String.format(CreateCommand.MESSAGE_SUCCESS,
                VALID_WORKOUT_NAME_ABS_WORKOUT), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(absWorkout), modelStub.workoutsAdded);
    }

    @Test
    public void execute_duplicateWorkout_throwsCommandException() {
        Workout validWorkout = new WorkoutBuilder().build();
        CreateCommand createCommand = new CreateCommand(ARMS_WORKOUT.getWorkoutName());
        ModelStub modelStub = new ModelStubWithWorkout(validWorkout);

        assertThrows(
                CommandException.class, CreateCommand.MESSAGE_DUPLICATE_WORKOUT, () ->
                createCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        CreateCommand addLegsWorkoutCommand = new CreateCommand(LEGS_WORKOUT.getWorkoutName());
        CreateCommand addArmsWorkoutCommand = new CreateCommand(ARMS_WORKOUT.getWorkoutName());

        // same object -> returns true
        assertTrue(addLegsWorkoutCommand.equals(addLegsWorkoutCommand));

        // same values -> returns true
        CreateCommand addLegsWorkoutCommandCopy = new CreateCommand(LEGS_WORKOUT.getWorkoutName());
        assertTrue(addLegsWorkoutCommandCopy.equals(addLegsWorkoutCommand));

        // different types -> returns false
        assertFalse(addLegsWorkoutCommand.equals(1));

        // null -> returns false
        assertFalse(addLegsWorkoutCommand.equals(null));

        // different workout -> returns false
        assertFalse(addLegsWorkoutCommand.equals(addArmsWorkoutCommand));
    }

    /**
     * A Model stub that contains a single exercise.
     */
    private class ModelStubWithWorkout extends ModelStub {
        private final Workout workout;

        ModelStubWithWorkout(Workout workout) {
            requireNonNull(workout);
            this.workout = workout;
        }

        @Override
        public boolean isInSession() {
            return false;
        }

        @Override
        public boolean hasWorkout(Workout workout) {
            requireNonNull(workout);
            return this.workout.isSameWorkout(workout);
        }
    }

    /**
     * A Model stub that always accept the exercise being added.
     */
    private class ModelStubAcceptingWorkoutAdded extends ModelStub {
        final ArrayList<Workout> workoutsAdded = new ArrayList<>();

        @Override
        public boolean isInSession() {
            return false;
        }

        @Override
        public boolean hasWorkout(Workout workout) {
            requireNonNull(workout);
            return workoutsAdded.stream().anyMatch(workout::isSameWorkout);
        }

        @Override
        public void addWorkout(Workout workout) {
            requireNonNull(workout);
            workoutsAdded.add(workout);
        }

        @Override
        public ReadOnlyWorkoutList getWorkoutList() {
            return new WorkoutList();
        }
    }

}
