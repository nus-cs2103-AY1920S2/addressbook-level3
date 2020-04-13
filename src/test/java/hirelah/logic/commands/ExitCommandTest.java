package hirelah.logic.commands;

import hirelah.commons.exceptions.IllegalValueException;
import hirelah.logic.commands.exceptions.CommandException;
import hirelah.model.Model;
import hirelah.model.ModelManager;
import hirelah.model.hirelah.Interviewee;
import org.junit.jupiter.api.Test;

import static hirelah.logic.commands.CloseReportCommand.MESSAGE_SUCCESS;
import static hirelah.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;
import static org.junit.jupiter.api.Assertions.*;

class ExitCommandTest {

    private ExitCommand command = new ExitCommand();

    @Test
    void execute_validExit_success() throws CommandException, IllegalValueException {
        StorageStub storage = new StorageStub();
        Model actualModel = new ModelManager();
        CommandResult expectedCommandResult = new ExitCommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
        CommandResult actualCommandResult = command.execute(actualModel, storage);
        assertEquals(expectedCommandResult, actualCommandResult);
    }

    @Test
    void test_Equals_success() {
        assertEquals(new ExitCommand(), command);
    }
}