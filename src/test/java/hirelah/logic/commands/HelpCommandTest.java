package hirelah.logic.commands;

import hirelah.commons.exceptions.IllegalValueException;
import hirelah.model.Model;
import hirelah.model.ModelManager;
import hirelah.model.hirelah.Interviewee;
import org.junit.jupiter.api.Test;

import static hirelah.logic.commands.CommandTestUtil.assertCommandSuccess;
import static hirelah.logic.commands.FinaliseCommand.MESSAGE_SUCCESS;
import static hirelah.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;
import static org.junit.jupiter.api.Assertions.*;

class HelpCommandTest {

    private HelpCommand command = new HelpCommand();

    @Test
    void execute_validHelp_success() throws IllegalValueException {
        StorageStub storage = new StorageStub();
        Model actualModel = new ModelManager();
        actualModel.setCurrentInterviewee(new Interviewee("Jane Doe", 1));
        CommandResult actualCommandResult = command.execute(actualModel, storage);
        CommandResult expectedCommandResult = new HelpCommandResult(SHOWING_HELP_MESSAGE);
        assertEquals(expectedCommandResult, actualCommandResult);
    }

    @Test
    void testEquals() {
    }
}