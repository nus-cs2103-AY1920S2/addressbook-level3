package seedu.zerotoone.logic.commands.exercise;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.zerotoone.testutil.Assert.assertThrows;
import static seedu.zerotoone.testutil.TypicalIndexes.INDEX_FIRST_OBJECT;
import static seedu.zerotoone.testutil.TypicalIndexes.INDEX_SECOND_OBJECT;
import static seedu.zerotoone.testutil.exercise.ExerciseCommandTestUtil.VALID_EXERCISE_NAME_BENCH_PRESS;
import static seedu.zerotoone.testutil.exercise.ExerciseCommandTestUtil.VALID_EXERCISE_NAME_DEADLIFT;
import static seedu.zerotoone.testutil.exercise.TypicalExercises.BENCH_PRESS;
import static seedu.zerotoone.testutil.exercise.TypicalExercises.DEADLIFT;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.zerotoone.commons.core.GuiSettings;
import seedu.zerotoone.commons.core.Messages;
import seedu.zerotoone.logic.commands.Command;
import seedu.zerotoone.logic.commands.CommandResult;
import seedu.zerotoone.logic.commands.exceptions.CommandException;
import seedu.zerotoone.model.Model;
import seedu.zerotoone.model.exercise.Exercise;
import seedu.zerotoone.model.exercise.ExerciseName;
import seedu.zerotoone.model.exercise.ReadOnlyExerciseList;
import seedu.zerotoone.model.log.ReadOnlyLogList;
import seedu.zerotoone.model.schedule.Schedule;
import seedu.zerotoone.model.schedule.ScheduleList;
import seedu.zerotoone.model.schedule.ScheduledWorkout;
import seedu.zerotoone.model.session.CompletedSet;
import seedu.zerotoone.model.session.CompletedWorkout;
import seedu.zerotoone.model.session.OngoingWorkout;
import seedu.zerotoone.model.session.ReadOnlyCompletedSetList;
import seedu.zerotoone.model.session.ReadOnlyOngoingSetList;
import seedu.zerotoone.model.session.ReadOnlyTimerList;
import seedu.zerotoone.model.userprefs.ReadOnlyUserPrefs;
import seedu.zerotoone.model.workout.ReadOnlyWorkoutList;
import seedu.zerotoone.model.workout.Workout;
import seedu.zerotoone.testutil.exercise.ExerciseBuilder;


public class EditCommandTest {
    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new EditCommand(null, new ExerciseName(VALID_EXERCISE_NAME_BENCH_PRESS)));
    }

    @Test
    public void constructor_nullExerciseName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new EditCommand(INDEX_FIRST_OBJECT, null));
    }

    @Test
    public void execute_isInSession_throwsCommandException() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_OBJECT,
                new ExerciseName(VALID_EXERCISE_NAME_BENCH_PRESS));
        ModelStub modelStub = new ModelStubInSession();
        assertThrows(CommandException.class, Command.MESSAGE_SESSION_STARTED, () ->
                editCommand.execute(modelStub));
    }
    
    @Test
    public void execute_invalidIndex_throwsCommandException() {
        EditCommand editCommand = new EditCommand(INDEX_SECOND_OBJECT,
                new ExerciseName(VALID_EXERCISE_NAME_BENCH_PRESS));
        Exercise validExercise = new ExerciseBuilder().build();
        ModelStub modelStub = new ModelStubOneExerciseEnableEditing(validExercise);

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_INDEX, () ->
                editCommand.execute(modelStub));
    }

    @Test
    public void execute_duplicateExercise_throwsCommandException() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_OBJECT, new ExerciseName(VALID_EXERCISE_NAME_BENCH_PRESS));
        Exercise validExercise = new ExerciseBuilder()
                .withExerciseName(VALID_EXERCISE_NAME_BENCH_PRESS) 
                .build();
        ModelStub modelStub = new ModelStubOneExerciseEnableEditing(validExercise);
        assertThrows(CommandException.class, EditCommand.MESSAGE_DUPLICATE_EXERCISE, () ->
                editCommand.execute(modelStub));
    }

    @Test
    public void execute_exerciseAcceptedByModel_editSuccessful() throws Exception {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_OBJECT, new ExerciseName(VALID_EXERCISE_NAME_DEADLIFT));
        Exercise validExercise = new ExerciseBuilder()
                .withExerciseName(VALID_EXERCISE_NAME_BENCH_PRESS).build();
        ModelStubOneExerciseEnableEditing modelStub = new ModelStubOneExerciseEnableEditing(validExercise);
        
        CommandResult commandResult = editCommand.execute(modelStub);
        Exercise exerciseDeadlift = new ExerciseBuilder()
                .withExerciseName(VALID_EXERCISE_NAME_DEADLIFT).build();

        assertEquals(String.format(EditCommand.MESSAGE_EDIT_EXERCISE_SUCCESS,
                VALID_EXERCISE_NAME_DEADLIFT), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(exerciseDeadlift), modelStub.exercises);
    }

    @Test
    public void equals() {
        EditCommand editBenchPressCommand = new EditCommand(INDEX_FIRST_OBJECT, BENCH_PRESS.getExerciseName());
        EditCommand editDeadliftCommand = new EditCommand(INDEX_FIRST_OBJECT, DEADLIFT.getExerciseName());
        EditCommand editBenchPressCommandTwo = new EditCommand(INDEX_SECOND_OBJECT, BENCH_PRESS.getExerciseName());

        // same object -> returns true
        assertTrue(editBenchPressCommand.equals(editBenchPressCommand));

        // same values -> returns true
        EditCommand editBenchPressCommandCopy = new EditCommand(INDEX_FIRST_OBJECT, BENCH_PRESS.getExerciseName());
        assertTrue(editBenchPressCommand.equals(editBenchPressCommandCopy));

        // different types -> returns false
        assertFalse(editBenchPressCommand.equals(1));

        // null -> returns false
        assertFalse(editBenchPressCommand.equals(null));

        // different exercise -> returns false
        assertFalse(editBenchPressCommand.equals(editDeadliftCommand));
        
        // different index -> returns false
        assertFalse(editBenchPressCommand.equals(editBenchPressCommandTwo));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getExerciseListFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setExerciseListFilePath(Path exerciseListFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addExercise(Exercise exercise) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setExerciseList(ReadOnlyExerciseList newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyExerciseList getExerciseList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasExercise(Exercise exercise) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteExercise(Exercise target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setExercise(Exercise target, Exercise editedExercise) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Exercise> getFilteredExerciseList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredExerciseList(Predicate<Exercise> predicate) {
            throw new AssertionError("This method should not be called.");
        }


        // -----------------------------------------------------------------------------------------
        // Workout
        @Override
        public Path getWorkoutListFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setWorkoutListFilePath(Path workoutListFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addWorkout(Workout workout) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setWorkoutList(ReadOnlyWorkoutList newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyWorkoutList getWorkoutList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasWorkout(Workout workout) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteWorkout(Workout target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteExerciseFromWorkouts(Exercise target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setWorkout(Workout target, Workout editedWorkout) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Workout> getFilteredWorkoutList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredWorkoutList(Predicate<Workout> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyOngoingSetList getOngoingSetList() {
            throw new AssertionError("This method should not be called.");
        }

        // -----------------------------------------------------------------------------------------
        // Session
        @Override
        public boolean isInSession() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public OngoingWorkout startSession(Workout workout, LocalDateTime currentDateTime) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void stopSession(LocalDateTime currentDateTime) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<OngoingWorkout> getCurrentWorkout() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public CompletedSet skip() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public CompletedSet done() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Boolean hasExerciseLeft() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyCompletedSetList getLastSet() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyTimerList getTimerList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void shutdownTimer() {
            throw new AssertionError("This method should not be called.");
        }

        // -----------------------------------------------------------------------------------------
        // Schedule
        @Override
        public boolean hasSchedule(Schedule schedule) {
            throw new AssertionError("This method should not be called.");
        }

        public void addSchedule(Schedule schedule) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSchedule(Schedule scheduleToEdit, Schedule editedSchedule) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteScheduledWorkout(ScheduledWorkout scheduledWorkoutToDelete) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<ScheduledWorkout> getSortedScheduledWorkoutList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ScheduleList getScheduleList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void populateSortedScheduledWorkoutList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyLogList getLogList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ArrayList<CompletedWorkout> getLogListCopyAsArrayList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<CompletedWorkout> getFilteredLogList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredLogList(Predicate<CompletedWorkout> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getLogListFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<LocalDateTime> getStatisticsStartDateRange() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<LocalDateTime> getStatisticsEndDateRange() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteLog(int target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setLogListFilePath(Path logListFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStatisticsDateRange(Optional<LocalDateTime> startRange, Optional<LocalDateTime> endRange) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that is in session.
     */
    private class ModelStubInSession extends ModelStub {
        @Override
        public boolean isInSession() {
            return true;
        }
    }

    /**
     * A Model stub that only contains one exercise and allows editing.
     */
    private class ModelStubOneExerciseEnableEditing extends ModelStub {
        public final List<Exercise> exercises = new ArrayList<>();

        ModelStubOneExerciseEnableEditing(Exercise exercise) {
            requireNonNull(exercise);
            this.exercises.add(exercise);
        }

        @Override
        public boolean isInSession() {
            return false;
        }

        @Override
        public boolean hasExercise(Exercise exercise) {
            requireNonNull(exercise);
            return exercises.stream().anyMatch(exercise::isSameExercise);
        }

        @Override
        public void setExercise(Exercise target, Exercise editedExercise) {
            requireNonNull(target);
            requireNonNull(editedExercise);
            exercises.remove(target);
            exercises.add(editedExercise);
        }

        @Override
        public ObservableList<Exercise> getFilteredExerciseList() {
            return FXCollections.observableArrayList(exercises);
        }

        @Override
        public void updateFilteredExerciseList(Predicate<Exercise> predicate) {}
    }

}
