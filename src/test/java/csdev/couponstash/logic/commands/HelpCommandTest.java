package csdev.couponstash.logic.commands;

import static csdev.couponstash.logic.commands.HelpCommand.ERROR;
import static csdev.couponstash.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import csdev.couponstash.logic.commands.exceptions.CommandException;

import csdev.couponstash.model.Model;
import csdev.couponstash.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();

    @Test
    public void execute_error() {
        HelpCommand helpCommand = new HelpCommand();
        assertThrows(CommandException.class, ERROR, () -> helpCommand.execute(model));
    }
}
