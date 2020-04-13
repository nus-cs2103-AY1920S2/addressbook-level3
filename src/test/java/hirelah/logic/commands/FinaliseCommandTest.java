package hirelah.logic.commands;

import static hirelah.logic.commands.CommandTestUtil.assertCommandSuccess;
import static hirelah.logic.commands.FinaliseCommand.MESSAGE_SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import hirelah.commons.exceptions.IllegalValueException;
import hirelah.model.Model;
import hirelah.model.ModelManager;

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
    void test_equals_success() {
        assertEquals(new FinaliseCommand(), command);
    }
}
