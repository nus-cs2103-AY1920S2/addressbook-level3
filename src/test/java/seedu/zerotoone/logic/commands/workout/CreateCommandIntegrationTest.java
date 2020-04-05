package seedu.zerotoone.logic.commands.workout;

import static seedu.zerotoone.testutil.CommandTestUtil.assertCommandSuccess;
import static seedu.zerotoone.testutil.exercise.TypicalExercises.getTypicalExerciseList;
import static seedu.zerotoone.testutil.workout.TypicalWorkouts.getTypicalWorkoutList;
import static seedu.zerotoone.testutil.workout.WorkoutCommandTestUtil.VALID_WORKOUT_NAME_ABS_WORKOUT;
import static seedu.zerotoone.testutil.workout.WorkoutCommandTestUtil.assertCommandFailure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.zerotoone.model.Model;
import seedu.zerotoone.model.ModelManager;
import seedu.zerotoone.model.log.LogList;
import seedu.zerotoone.model.schedule.ScheduleList;
import seedu.zerotoone.model.userprefs.UserPrefs;
import seedu.zerotoone.model.workout.Workout;
import seedu.zerotoone.model.workout.WorkoutName;
import seedu.zerotoone.testutil.workout.WorkoutBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code CreateCommand}.
 */
public class CreateCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new UserPrefs(),
                getTypicalExerciseList(),
                getTypicalWorkoutList(),
                new ScheduleList(),
                new LogList());
    }

    @Test
    public void execute_newWorkout_success() {
        Workout validWorkout = new WorkoutBuilder().withWorkoutName("Abs Workout").build();

        Model expectedModel = new ModelManager(new UserPrefs(),
                model.getExerciseList(),
                model.getWorkoutList(),
                model.getScheduleList(),
                model.getLogList());

        expectedModel.addWorkout(validWorkout);

        CreateCommand command = new CreateCommand(new WorkoutName(VALID_WORKOUT_NAME_ABS_WORKOUT));
        assertCommandSuccess(command, model,
                String.format(CreateCommand.MESSAGE_SUCCESS, validWorkout.getWorkoutName()), expectedModel);
    }

    @Test
    public void execute_duplicateWorkout_throwsCommandException() {
        Workout workoutInList = model.getWorkoutList().getWorkoutList().get(0);
        assertCommandFailure(new CreateCommand(workoutInList.getWorkoutName()), model,
                CreateCommand.MESSAGE_DUPLICATE_WORKOUT);
    }

}
