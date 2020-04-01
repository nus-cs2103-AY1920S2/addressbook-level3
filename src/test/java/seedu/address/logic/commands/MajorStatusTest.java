package seedu.address.logic.commands;

import static seedu.address.model.util.SampleDataUtil.getSamplePlanner;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.major.MajorSetCommand;
import seedu.address.logic.commands.major.MajorStatusCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class MajorStatusTest {

    private Model model = new ModelManager(getSamplePlanner(), new UserPrefs());

    @Test
    public void execute_majorStatus_success() {
        String major = "CS";
        MajorSetCommand majorSetCommand = new MajorSetCommand(major);
        String expectedMessage = String.format(MajorSetCommand.MESSAGE_SUCCESS, major);
        MajorStatusCommand majorStatusCommand = new MajorStatusCommand();
    }

    @Test
    public void equals() {
    }
}
