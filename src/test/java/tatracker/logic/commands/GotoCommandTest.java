package tatracker.logic.commands;

import static tatracker.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tatracker.logic.commands.commons.GotoCommand.MESSAGE_SWITCHED_TAB;

import org.junit.jupiter.api.Test;

import tatracker.logic.commands.CommandResult.Action;
import tatracker.logic.commands.commons.GotoCommand;
import tatracker.logic.commands.commons.GotoCommand.Tab;
import tatracker.model.Model;
import tatracker.model.ModelManager;

public class GotoCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_gotostudent_success() {
        CommandResult expectedCommandResult = new CommandResult(String.format(MESSAGE_SWITCHED_TAB, "student"),
                Action.GOTO_STUDENT);
        assertCommandSuccess(new GotoCommand(Tab.STUDENT), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_gotosession_success() {
        CommandResult expectedCommandResult = new CommandResult(String.format(MESSAGE_SWITCHED_TAB, "session"),
                Action.GOTO_SESSION);
        assertCommandSuccess(new GotoCommand(Tab.SESSION), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_gotoclaims_success() {
        CommandResult expectedCommandResult = new CommandResult(String.format(MESSAGE_SWITCHED_TAB, "claims"),
                Action.GOTO_CLAIMS);
        assertCommandSuccess(new GotoCommand(Tab.CLAIMS), model, expectedCommandResult, expectedModel);
    }
}
