package seedu.zerotoone.logic.commands.exercise;

import static seedu.zerotoone.testutil.CommandTestUtil.assertCommandSuccess;
import static seedu.zerotoone.testutil.exercise.ExerciseCommandTestUtil.VALID_EXERCISE_NAME_OVERHEAD_PRESS;
import static seedu.zerotoone.testutil.exercise.ExerciseCommandTestUtil.assertCommandFailure;
import static seedu.zerotoone.testutil.exercise.TypicalExercises.getTypicalExerciseList;
import static seedu.zerotoone.testutil.workout.TypicalWorkouts.getTypicalWorkoutList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.zerotoone.model.Model;
import seedu.zerotoone.model.ModelManager;
import seedu.zerotoone.model.exercise.Exercise;
import seedu.zerotoone.model.exercise.ExerciseName;
import seedu.zerotoone.model.schedule.ScheduleList;
import seedu.zerotoone.model.session.SessionList;
import seedu.zerotoone.model.userprefs.UserPrefs;
import seedu.zerotoone.testutil.exercise.ExerciseBuilder;

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
                new SessionList());
    }

    @Test
    public void execute_newExercise_success() {
        Exercise validExercise = new ExerciseBuilder()
                .withExerciseName(VALID_EXERCISE_NAME_OVERHEAD_PRESS).build();

        Model expectedModel = new ModelManager(new UserPrefs(),
                model.getExerciseList(),
                model.getWorkoutList(),
                model.getScheduleList(),
                model.getSessionList());

        expectedModel.addExercise(validExercise);

        CreateCommand command = new CreateCommand(new ExerciseName(VALID_EXERCISE_NAME_OVERHEAD_PRESS));
        assertCommandSuccess(command, model,
                String.format(CreateCommand.MESSAGE_SUCCESS, validExercise.getExerciseName()), expectedModel);
    }

    @Test
    public void execute_duplicateExercise_throwsCommandException() {
        Exercise exerciseInList = model.getExerciseList().getExerciseList().get(0);
        assertCommandFailure(new CreateCommand(exerciseInList.getExerciseName()), model,
                CreateCommand.MESSAGE_DUPLICATE_EXERCISE);
    }

}
