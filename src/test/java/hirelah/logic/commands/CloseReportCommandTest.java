package hirelah.logic.commands;

import hirelah.commons.exceptions.IllegalValueException;
import hirelah.logic.commands.exceptions.CommandException;
import hirelah.logic.parser.AddCommandParser;
import hirelah.model.Model;
import hirelah.model.ModelManager;
import hirelah.model.hirelah.AttributeList;
import hirelah.model.hirelah.Interviewee;
import org.junit.jupiter.api.Test;

import static hirelah.logic.commands.CloseReportCommand.MESSAGE_SUCCESS;
import static hirelah.logic.commands.CommandTestUtil.assertCommandSuccess;
import static hirelah.logic.commands.CommandTestUtility.VALID_ATTRIBUTE_INTEGRITY;
import static org.junit.jupiter.api.Assertions.*;

class CloseReportCommandTest {

    private CloseReportCommand command = new CloseReportCommand();

    @Test
    void execute_validClose_success() throws CommandException, IllegalValueException {
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
    void test_Equals_Success() {
        assertEquals(new CloseReportCommand(), command);
    }
}