package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalModManager.getTypicalModManager;

import org.junit.jupiter.api.Test;

import seedu.address.model.ModManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyModManager_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, CommandType.CLEAR, expectedModel);
    }

    @Test
    public void execute_nonEmptyModManager_success() {
        Model model = new ModelManager(getTypicalModManager(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalModManager(), new UserPrefs());
        expectedModel.setModManager(new ModManager());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, CommandType.CLEAR, expectedModel);
    }

}
