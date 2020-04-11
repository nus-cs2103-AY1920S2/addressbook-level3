package cookbuddy.logic.commands;

import static cookbuddy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static cookbuddy.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import cookbuddy.model.Model;
import cookbuddy.model.ModelManager;
import org.junit.jupiter.api.Test;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true, false);
        assertCommandSuccess(new HelpCommand(""), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void get_help_message() {
        String commandWord = "delete";
        HelpCommand helpMessage = new HelpCommand(commandWord);
        assertEquals(commandWord,helpMessage.getCommandWord());
    }
}
