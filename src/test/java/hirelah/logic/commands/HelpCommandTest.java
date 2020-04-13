package hirelah.logic.commands;

import static hirelah.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import hirelah.commons.exceptions.IllegalValueException;
import hirelah.model.Model;
import hirelah.model.ModelManager;
import hirelah.model.hirelah.Interviewee;

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
}
