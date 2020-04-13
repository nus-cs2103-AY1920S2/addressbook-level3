package hirelah.logic.commands;

import static hirelah.logic.commands.CloseReportCommand.MESSAGE_SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import hirelah.commons.exceptions.IllegalValueException;
import hirelah.logic.commands.exceptions.CommandException;
import hirelah.model.Model;
import hirelah.model.ModelManager;
import hirelah.model.hirelah.Interviewee;

class CloseReportCommandTest {

    private CloseReportCommand command = new CloseReportCommand();

    @Test
    void execute_validCloseReport_success() throws CommandException, IllegalValueException {
        StorageStub storage = new StorageStub();
        Model actualModel = new ModelManager();
        actualModel.setCurrentInterviewee(new Interviewee("Jane Doe", 1));
        CommandResult expectedCommandResult = new ToggleCommandResult(String.format(MESSAGE_SUCCESS, "Jane Doe"),
                ToggleView.CLOSE_TRANSCRIPT);
        CommandResult actualCommandResult = command.execute(actualModel, storage);
        assertEquals(actualCommandResult, expectedCommandResult);
    }

    @Test
    void execute_invalidClose_failure() {
        StorageStub storage = new StorageStub();
        Model actualModel = new ModelManager();
        assertThrows(CommandException.class, () -> command.execute(actualModel, storage));
    }

    @Test
    void test_equals_success() {
        assertEquals(new CloseReportCommand(), command);
    }
}
