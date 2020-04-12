package seedu.zerotoone.logic.commands.schedule;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.zerotoone.testutil.Assert.assertThrows;
import static seedu.zerotoone.testutil.TypicalIndexes.INDEX_FIRST_OBJECT;
import static seedu.zerotoone.testutil.TypicalIndexes.INDEX_SECOND_OBJECT;
import static seedu.zerotoone.testutil.exercise.TypicalExercises.getTypicalExerciseList;
import static seedu.zerotoone.testutil.schedule.ScheduleCommandTestUtil.VALID_DATETIME_IN_THE_PAST;
import static seedu.zerotoone.testutil.schedule.ScheduleCommandTestUtil.VALID_DATETIME_JUNE;
import static seedu.zerotoone.testutil.workout.TypicalWorkouts.getTypicalWorkoutList;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.zerotoone.commons.core.Messages;
import seedu.zerotoone.commons.core.index.Index;
import seedu.zerotoone.logic.commands.Command;
import seedu.zerotoone.logic.commands.CommandResult;
import seedu.zerotoone.logic.commands.exceptions.CommandException;
import seedu.zerotoone.model.Model;
import seedu.zerotoone.model.ModelManager;
import seedu.zerotoone.model.log.LogList;
import seedu.zerotoone.model.schedule.DateTime;
import seedu.zerotoone.model.schedule.Schedule;
import seedu.zerotoone.model.schedule.ScheduleList;
import seedu.zerotoone.model.userprefs.UserPrefs;
import seedu.zerotoone.model.workout.Workout;
import seedu.zerotoone.model.workout.WorkoutList;
import seedu.zerotoone.model.workout.WorkoutName;
import seedu.zerotoone.testutil.ModelStub;
import seedu.zerotoone.testutil.schedule.OneTimeScheduleBuilder;

public class CreateCommandTest {

    private Model model = new ModelManager(new UserPrefs(),
            getTypicalExerciseList(),
            getTypicalWorkoutList(),
            new ScheduleList(),
            new LogList());

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CreateCommand(null, null));
    }

    @Test
    public void execute_scheduleAcceptedByModel_addSuccessful() throws Exception {
        Index index = INDEX_FIRST_OBJECT;
        WorkoutName workoutName = model.getFilteredWorkoutList().get(index.getZeroBased()).getWorkoutName();
        DateTime dateTime = new DateTime(VALID_DATETIME_JUNE);
        CreateCommand createCommand = new CreateCommand(index, dateTime);

        ModelStubAcceptingScheduleAdded modelStub = new ModelStubAcceptingScheduleAdded();
        CommandResult commandResult = createCommand.execute(modelStub);
        Schedule expectedSchedule = new OneTimeScheduleBuilder()
                .withWorkoutName(workoutName)
                .withDateTime(dateTime)
                .build();

        assertEquals(
                String.format(CreateCommand.MESSAGE_SUCCESS, expectedSchedule),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(expectedSchedule), modelStub.schedulesAdded);
    }

    @Test
    public void execute_modelInSession_throwsCommandException() {
        DateTime dateTime = new DateTime(VALID_DATETIME_JUNE);
        CreateCommand createCommand = new CreateCommand(INDEX_FIRST_OBJECT, dateTime);
        Model model = new ModelStubInSession();

        assertThrows(CommandException.class,
                Command.MESSAGE_SESSION_STARTED, () -> createCommand.execute(model));
    }

    @Test
    public void execute_datetimeInThePast_throwsCommandException() {
        DateTime dateTime = new DateTime(VALID_DATETIME_IN_THE_PAST);
        CreateCommand createCommand = new CreateCommand(INDEX_FIRST_OBJECT, dateTime);

        assertThrows(CommandException.class,
                CreateCommand.MESSAGE_DATETIME_IN_THE_PAST, () -> createCommand.execute(model));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        DateTime dateTime = new DateTime(VALID_DATETIME_JUNE);
        CreateCommand createCommand = new CreateCommand(INDEX_FIRST_OBJECT, dateTime);

        Model modelWithNoWorkout = new ModelManager(model.getUserPrefs(),
                model.getExerciseList(),
                new WorkoutList(),
                model.getScheduleList(),
                model.getLogList());

        assertThrows(CommandException.class,
                Messages.MESSAGE_INVALID_INDEX, () -> createCommand.execute(modelWithNoWorkout));
    }

    @Test
    public void execute_duplicateSchedule_throwsCommandException() {
        WorkoutName workoutName = model.getFilteredWorkoutList().get(0).getWorkoutName();
        DateTime dateTime = new DateTime(VALID_DATETIME_JUNE);
        CreateCommand createCommand = new CreateCommand(INDEX_FIRST_OBJECT, dateTime);

        Schedule expectedSchedule = new OneTimeScheduleBuilder()
                .withWorkoutName(workoutName)
                .withDateTime(dateTime)
                .build();

        Model modelWithSchedule = new ModelStubWithSchedule(expectedSchedule);

        assertThrows(CommandException.class,
                CreateCommand.MESSAGE_DUPLICATE_SCHEDULE, () -> createCommand.execute(modelWithSchedule));
    }

    @Test
    public void equals() {
        DateTime dateTime = new DateTime(VALID_DATETIME_JUNE);
        CreateCommand addFirstWorkoutCommand = new CreateCommand(INDEX_FIRST_OBJECT, dateTime);
        CreateCommand addSecondWorkoutCommand = new CreateCommand(INDEX_SECOND_OBJECT, dateTime);

        // same object -> returns true
        assertTrue(addFirstWorkoutCommand.equals(addFirstWorkoutCommand));

        // same values -> returns true
        CreateCommand addFirstWorkoutCommandCopy = new CreateCommand(INDEX_FIRST_OBJECT, dateTime);
        assertTrue(addFirstWorkoutCommand.equals(addFirstWorkoutCommandCopy));

        // different types -> returns false
        assertFalse(addFirstWorkoutCommand.equals(1));

        // null -> returns false
        assertFalse(addFirstWorkoutCommand.equals(null));

        // different index -> returns false
        assertFalse(addFirstWorkoutCommand.equals(addSecondWorkoutCommand));
    }

    /**
     * A Model stub that contains a single schedule.
     */
    private class ModelStubWithSchedule extends ModelStub {
        private final Schedule schedule;

        ModelStubWithSchedule(Schedule schedule) {
            requireNonNull(schedule);
            this.schedule = schedule;
        }

        @Override
        public boolean isInSession() {
            return false;
        }

        @Override
        public boolean hasSchedule(Schedule schedule) {
            requireNonNull(schedule);
            return this.schedule.isSameSchedule(schedule);
        }

        @Override
        public ObservableList<Workout> getFilteredWorkoutList() {
            return model.getFilteredWorkoutList();
        }
    }

    /**
     * A Model stub that always accept the schedule being added.
     */
    private class ModelStubAcceptingScheduleAdded extends ModelStub {
        final ArrayList<Schedule> schedulesAdded = new ArrayList<>();

        @Override
        public boolean isInSession() {
            return false;
        }

        @Override
        public boolean hasSchedule(Schedule schedule) {
            requireNonNull(schedule);
            return schedulesAdded.stream().anyMatch(schedule::isSameSchedule);
        }

        @Override
        public void addSchedule(Schedule schedule) {
            requireNonNull(schedule);
            schedulesAdded.add(schedule);
        }

        @Override
        public ObservableList<Workout> getFilteredWorkoutList() {
            return model.getFilteredWorkoutList();
        }
    }

    /**
     * A Model stub that is always in session.
     */
    private class ModelStubInSession extends ModelStub {
        @Override
        public boolean isInSession() {
            return true;
        }
    }
}
