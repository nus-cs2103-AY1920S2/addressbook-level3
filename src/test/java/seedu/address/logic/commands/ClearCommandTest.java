package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalExerciseList;

import org.junit.jupiter.api.Test;

import seedu.address.model.ExerciseList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

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
