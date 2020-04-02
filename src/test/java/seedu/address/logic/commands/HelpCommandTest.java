package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_helpChooseCommand_success() {
        int index = 5;
        CommandResult expectedCommandResult = new CommandResult(HelpCommand.LIST_OF_COMMANDS.get(index - 1) + "\n"
                + "Type \"help\" to return to the list of commands.", true, false, false,
                false, false, false, false, false);
        assertCommandSuccess(new HelpCommand(index), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_helpList_success() {
        CommandResult expectedCommandResult = new CommandResult(HelpCommand.MESSAGE, true, false,
            false, false, false, false, false, false);
        assertCommandSuccess(new HelpCommand(-1), model, expectedCommandResult, expectedModel);
    }
}
