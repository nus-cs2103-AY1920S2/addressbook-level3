package seedu.jelphabot.logic.commands;

import static seedu.jelphabot.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jelphabot.testutil.TypicalTasks.getTypicalJelphaBot;

import org.junit.jupiter.api.Test;

import seedu.jelphabot.model.JelphaBot;
import seedu.jelphabot.model.Model;
import seedu.jelphabot.model.ModelManager;
import seedu.jelphabot.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyJelphaBot_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyJelphaBot_success() {
        Model model = new ModelManager(getTypicalJelphaBot(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalJelphaBot(), new UserPrefs());
        expectedModel.setJelphaBot(new JelphaBot());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
