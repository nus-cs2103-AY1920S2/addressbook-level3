package fithelper.logic.commands;

import static fithelper.logic.commands.CommandTestUtil.assertCommandSuccess;
import static fithelper.logic.commands.HelpCommand.MESSAGE_SUCCESS;

import org.junit.jupiter.api.Test;

import fithelper.model.Model;
import fithelper.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult =
                new CommandResult(MESSAGE_SUCCESS, CommandResult.DisplayedPage.HELP, false);
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }
}

