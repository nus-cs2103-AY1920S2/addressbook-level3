package seedu.zerotoone.logic.commands.exercise;

import static seedu.zerotoone.testutil.exercise.ExerciseCommandTestUtil.assertCommandFailure;
import static seedu.zerotoone.testutil.exercise.ExerciseCommandTestUtil.assertCommandSuccess;
import static seedu.zerotoone.testutil.exercise.TypicalExercises.getTypicalExerciseList;
import static seedu.zerotoone.testutil.exercise.TypicalExercises.BENCH_PRESS;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.zerotoone.model.Model;
import seedu.zerotoone.model.ModelManager;
import seedu.zerotoone.model.exercise.Exercise;
import seedu.zerotoone.model.userprefs.UserPrefs;
import seedu.zerotoone.testutil.exercise.ExerciseBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code CreateCommand}.
 */
public class CreateCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalExerciseList(), new UserPrefs());
    }

    @Test
    public void execute_newExercise_success() {
        Exercise validExercise = new ExerciseBuilder().build();

        Model expectedModel = new ModelManager(model.getExerciseList(), new UserPrefs());
        expectedModel.addExercise(validExercise);

        assertCommandSuccess(new CreateCommand(BENCH_PRESS.getExerciseName()), model,
                String.format(CreateCommand.MESSAGE_SUCCESS, validExercise), expectedModel);
    }

    @Test
    public void execute_duplicateExercise_throwsCommandException() {
        Exercise exerciseInList = model.getExerciseList().getExerciseList().get(0);
        assertCommandFailure(new CreateCommand(exerciseInList.getExerciseName()), model, CreateCommand.MESSAGE_DUPLICATE_EXERCISE);
    }

}
