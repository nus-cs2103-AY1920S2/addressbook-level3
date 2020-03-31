package tatracker.logic.commands;

import static tatracker.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tatracker.testutil.TypicalStudents.getTypicalTaTracker;

import org.junit.jupiter.api.Test;

import tatracker.logic.commands.commons.ClearCommand;
import tatracker.model.Model;
import tatracker.model.ModelManager;
import tatracker.model.TaTracker;
import tatracker.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyTaTracker_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyTaTracker_success() {
        Model model = new ModelManager(getTypicalTaTracker(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalTaTracker(), new UserPrefs());
        expectedModel.setTaTracker(new TaTracker());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
