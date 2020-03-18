package seedu.zerotoone.logic.commands;

import static seedu.zerotoone.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.zerotoone.testutil.TypicalExercises.getTypicalExerciseList;

import org.junit.jupiter.api.Test;

import seedu.zerotoone.model.Model;
import seedu.zerotoone.model.ModelManager;
import seedu.zerotoone.model.exercise.ExerciseList;
import seedu.zerotoone.model.userprefs.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyExerciseList_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyExerciseList_success() {
        Model model = new ModelManager(getTypicalExerciseList(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalExerciseList(), new UserPrefs());
        expectedModel.setExerciseList(new ExerciseList());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
