package tatracker.logic.commands;

import static tatracker.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tatracker.logic.commands.GotoCommand.SHOWING_GOTO_MESSAGE;

import org.junit.jupiter.api.Test;

import tatracker.logic.commands.CommandResult.Action;
import tatracker.model.Model;
import tatracker.model.ModelManager;

public class GotoCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_gotostudent_success() {
        CommandResult expectedCommandResult = new CommandResult(String.format(SHOWING_GOTO_MESSAGE, "student"),
                Action.GOTO_STUDENT);
        assertCommandSuccess(new GotoCommand("student"), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_gotosession_success() {
        CommandResult expectedCommandResult = new CommandResult(String.format(SHOWING_GOTO_MESSAGE, "session"),
                Action.GOTO_SESSION);
        assertCommandSuccess(new GotoCommand("session"), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_gotoclaims_success() {
        CommandResult expectedCommandResult = new CommandResult(String.format(SHOWING_GOTO_MESSAGE, "claims"),
                Action.GOTO_CLAIMS);
        assertCommandSuccess(new GotoCommand("claims"), model, expectedCommandResult, expectedModel);
    }
}
