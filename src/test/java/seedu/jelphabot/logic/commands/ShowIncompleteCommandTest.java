//@@author eedenong
package seedu.jelphabot.logic.commands;

import static seedu.jelphabot.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jelphabot.logic.commands.CommandTestUtil.showTasksWithSpecifiedStatus;
import static seedu.jelphabot.logic.commands.ShowIncompleteCommand.MESSAGE_SUCCESS;
import static seedu.jelphabot.testutil.TypicalTasks.getTypicalJelphaBot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jelphabot.model.Model;
import seedu.jelphabot.model.ModelManager;
import seedu.jelphabot.model.UserPrefs;
import seedu.jelphabot.model.task.Status;

/**
 * Contains integration tests (interaction with Model) and unit tests for ShowIncompleteCommand.
 */
public class ShowIncompleteCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalJelphaBot(), new UserPrefs());
        expectedModel = new ModelManager(model.getJelphaBot(), new UserPrefs());
        showTasksWithSpecifiedStatus(expectedModel, Status.INCOMPLETE);
    }

    @Test
    public void execute_listIsFilteredByPredicate_showsIncompleteTasks() {
        showTasksWithSpecifiedStatus(model, Status.INCOMPLETE);
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS).isShowDateTaskList();
        assertCommandSuccess(new ShowIncompleteCommand(), model, expectedCommandResult, expectedModel);
    }

}
