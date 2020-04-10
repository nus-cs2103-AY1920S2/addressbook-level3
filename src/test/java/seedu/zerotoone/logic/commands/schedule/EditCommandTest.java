package seedu.zerotoone.logic.commands.schedule;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.zerotoone.testutil.Assert.assertThrows;
import static seedu.zerotoone.testutil.CommandTestUtil.assertCommandSuccess;
import static seedu.zerotoone.testutil.TypicalIndexes.INDEX_FIRST_OBJECT;
import static seedu.zerotoone.testutil.TypicalIndexes.INDEX_SECOND_OBJECT;
import static seedu.zerotoone.testutil.exercise.TypicalExercises.getTypicalExerciseList;
import static seedu.zerotoone.testutil.schedule.ScheduleCommandTestUtil.VALID_DATETIME_IN_THE_PAST;
import static seedu.zerotoone.testutil.schedule.ScheduleCommandTestUtil.VALID_DATETIME_JULY;
import static seedu.zerotoone.testutil.schedule.ScheduleCommandTestUtil.VALID_DATETIME_JUNE;
import static seedu.zerotoone.testutil.workout.TypicalWorkouts.getTypicalWorkoutList;

import org.junit.jupiter.api.Test;

import seedu.zerotoone.commons.core.Messages;
import seedu.zerotoone.logic.commands.Command;
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
import seedu.zerotoone.testutil.ModelStub;
import seedu.zerotoone.testutil.schedule.OneTimeScheduleBuilder;

class EditCommandTest {

    private Model model = new ModelManager(new UserPrefs(),
            getTypicalExerciseList(),
            getTypicalWorkoutList(),
            new ScheduleList(),
            new LogList());

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EditCommand(null, null));
    }

    @Test
    public void execute_modelInSession_throwsCommandException() {
        DateTime dateTime = new DateTime(VALID_DATETIME_JUNE);
        EditCommand editCommand = new EditCommand(INDEX_FIRST_OBJECT, dateTime);
        Model model = new ModelStubInSession();

        assertThrows(CommandException.class,
                Command.MESSAGE_SESSION_STARTED, () -> editCommand.execute(model));
    }

    @Test
    public void execute_scheduleAcceptedByModel_editSuccessful() {
        Workout workout = model.getFilteredWorkoutList().get(0);

        DateTime juneDateTime = new DateTime(VALID_DATETIME_JUNE);
        Schedule juneSchedule = new OneTimeScheduleBuilder().withWorkout(workout).withDateTime(juneDateTime).build();
        Model modelWithJuneSchedule = new ModelManager(model.getUserPrefs(),
                model.getExerciseList(),
                model.getWorkoutList(),
                new ScheduleList(model.getScheduleList()),
                model.getLogList());
        modelWithJuneSchedule.addSchedule(juneSchedule);

        DateTime julyDateTime = new DateTime(VALID_DATETIME_JULY); // TODO
        Schedule julySchedule = new OneTimeScheduleBuilder().withWorkout(workout).withDateTime(julyDateTime).build();
        Model modelWithJulySchedule = new ModelManager(model.getUserPrefs(),
                model.getExerciseList(),
                model.getWorkoutList(),
                new ScheduleList(model.getScheduleList()),
                model.getLogList());
        modelWithJulySchedule.addSchedule(julySchedule);


        EditCommand editCommand = new EditCommand(INDEX_FIRST_OBJECT, julyDateTime);
        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_SCHEDULE_SUCCESS, julySchedule);

        assertCommandSuccess(editCommand, modelWithJuneSchedule, expectedMessage, modelWithJulySchedule);
    }

    @Test
    public void execute_datetimeInThePast_throwsCommandException() {
        DateTime dateTime = new DateTime(VALID_DATETIME_IN_THE_PAST);
        EditCommand editCommand = new EditCommand(INDEX_FIRST_OBJECT, dateTime);

        assertThrows(CommandException.class,
                EditCommand.MESSAGE_DATETIME_IN_THE_PAST, () -> editCommand.execute(model));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        DateTime dateTime = new DateTime(VALID_DATETIME_JUNE);
        EditCommand editCommand = new EditCommand(INDEX_FIRST_OBJECT, dateTime);

        Model modelWithNoWorkout = new ModelManager(model.getUserPrefs(),
                model.getExerciseList(),
                new WorkoutList(),
                model.getScheduleList(),
                model.getLogList());

        assertThrows(CommandException.class,
                Messages.MESSAGE_INVALID_INDEX, () -> editCommand.execute(modelWithNoWorkout));
    }

    @Test
    public void execute_duplicateSchedule_throwsCommandException() {
        Workout workout = model.getFilteredWorkoutList().get(0);

        DateTime juneDateTime = new DateTime(VALID_DATETIME_JUNE);
        Schedule juneSchedule = new OneTimeScheduleBuilder().withWorkout(workout).withDateTime(juneDateTime).build();

        DateTime julyDateTime = new DateTime(VALID_DATETIME_JULY);
        Schedule julySchedule = new OneTimeScheduleBuilder().withWorkout(workout).withDateTime(julyDateTime).build();
        Model modelWithJulySchedule = new ModelManager(model.getUserPrefs(),
                model.getExerciseList(),
                model.getWorkoutList(),
                new ScheduleList(model.getScheduleList()),
                model.getLogList());

        modelWithJulySchedule.addSchedule(juneSchedule);
        modelWithJulySchedule.addSchedule(julySchedule);

        EditCommand editCommand = new EditCommand(INDEX_FIRST_OBJECT, julyDateTime);

        assertThrows(CommandException.class,
                EditCommand.MESSAGE_DUPLICATE_SCHEDULE, () -> editCommand.execute(modelWithJulySchedule));
    }

    @Test
    public void equals() {
        DateTime dateTime = new DateTime(VALID_DATETIME_JUNE);
        EditCommand addFirstWorkoutCommand = new EditCommand(INDEX_FIRST_OBJECT, dateTime);
        EditCommand addSecondWorkoutCommand = new EditCommand(INDEX_SECOND_OBJECT, dateTime);

        // same object -> returns true
        assertTrue(addFirstWorkoutCommand.equals(addFirstWorkoutCommand));

        // same values -> returns true
        EditCommand addFirstWorkoutCommandCopy = new EditCommand(INDEX_FIRST_OBJECT, dateTime);
        assertTrue(addFirstWorkoutCommand.equals(addFirstWorkoutCommandCopy));

        // different types -> returns false
        assertFalse(addFirstWorkoutCommand.equals(1));

        // null -> returns false
        assertFalse(addFirstWorkoutCommand.equals(null));

        // different index -> returns false
        assertFalse(addFirstWorkoutCommand.equals(addSecondWorkoutCommand));
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
