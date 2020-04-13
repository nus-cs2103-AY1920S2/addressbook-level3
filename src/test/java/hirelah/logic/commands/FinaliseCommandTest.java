package hirelah.logic.commands;

import hirelah.commons.exceptions.IllegalValueException;
import hirelah.model.Model;
import hirelah.model.ModelManager;
import hirelah.model.hirelah.AttributeList;
import org.junit.jupiter.api.Test;

import static hirelah.logic.commands.CommandTestUtil.assertCommandSuccess;
import static hirelah.logic.commands.CommandTestUtility.VALID_ATTRIBUTE_INTEGRITY;
import static hirelah.logic.commands.FinaliseCommand.MESSAGE_SUCCESS;
import static org.junit.jupiter.api.Assertions.*;

class FinaliseCommandTest {

    private FinaliseCommand command = new FinaliseCommand();

    @Test
    void execute_validFinalise_success() throws IllegalValueException {
        StorageStub storage = new StorageStub();
        Model actual = new ModelManager();

        Model expected = new ModelManager();
        expected.finaliseInterviewProperties();
        CommandResult expectedCommandResult =
                new ToggleCommandResult(MESSAGE_SUCCESS, ToggleView.INTERVIEWEE);

        assertCommandSuccess(command, actual, expectedCommandResult, expected, storage,
                expected::isFinalisedInterviewProperties);
    }

    @Test
    void testEquals() {
        assertEquals(new FinaliseCommand(), command);
    }
}