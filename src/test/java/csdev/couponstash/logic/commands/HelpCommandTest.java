package csdev.couponstash.logic.commands;

import static csdev.couponstash.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import csdev.couponstash.model.Model;
import csdev.couponstash.model.ModelManager;

import java.util.Optional;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult =
                new CommandResult(
                        HelpCommand.SHOWING_HELP_MESSAGE,
                        Optional.empty(),
                        Optional.empty(),
                        true,
                        false
                );

        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }
}
