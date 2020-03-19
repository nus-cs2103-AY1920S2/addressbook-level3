package seedu.zerotoone.logic.commands.exercise;

import static seedu.zerotoone.testutil.CommandTestUtil.assertCommandFailure;
import static seedu.zerotoone.testutil.CommandTestUtil.assertCommandSuccess;
import static seedu.zerotoone.testutil.exercise.TypicalExercises.getTypicalExerciseList;

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

        assertCommandSuccess(new CreateCommand(validExercise), model,
                String.format(CreateCommand.MESSAGE_SUCCESS, validExercise), expectedModel);
    }

    @Test
    public void execute_duplicateExercise_throwsCommandException() {
        Exercise exerciseInList = model.getExerciseList().getExerciseList().get(0);
        assertCommandFailure(new CreateCommand(exerciseInList), model, CreateCommand.MESSAGE_DUPLICATE_EXERCISE);
    }

}
