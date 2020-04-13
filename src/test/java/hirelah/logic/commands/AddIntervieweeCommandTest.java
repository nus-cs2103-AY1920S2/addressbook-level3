package hirelah.logic.commands;

import static hirelah.logic.commands.CommandTestUtil.assertCommandSuccess;
import static hirelah.logic.commands.CommandTestUtility.VALID_INTERVIEWEE_JANE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import hirelah.commons.exceptions.IllegalValueException;
import hirelah.model.Model;
import hirelah.model.ModelManager;
import hirelah.model.hirelah.IntervieweeList;

class AddIntervieweeCommandTest {

    @Test
    void execute() throws IllegalValueException {
        StorageStub storage = new StorageStub();
        Model actual = new ModelManager();
        actual.setIntervieweeList(new IntervieweeList());
        IntervieweeList expectedIntervieweeList = new IntervieweeList();
        expectedIntervieweeList.addInterviewee(VALID_INTERVIEWEE_JANE);
        Model expected = new ModelManager();
        expected.setIntervieweeList(expectedIntervieweeList);
        AddIntervieweeCommand command = new AddIntervieweeCommand(VALID_INTERVIEWEE_JANE);
        CommandResult expectedCommandResult =
                new ToggleCommandResult(String.format(AddIntervieweeCommand.MESSAGE_SUCCESS, VALID_INTERVIEWEE_JANE),
                        ToggleView.INTERVIEWEE);

        assertCommandSuccess(command, actual, expectedCommandResult, expected, storage,
                storage::isIntervieweesSaved);
    }

    @Test
    void testEquals() {
        assertEquals(new AddIntervieweeCommand(VALID_INTERVIEWEE_JANE),
                new AddIntervieweeCommand(VALID_INTERVIEWEE_JANE));
        assertEquals(new AddIntervieweeCommand(VALID_INTERVIEWEE_JANE, "jan"),
                new AddIntervieweeCommand(VALID_INTERVIEWEE_JANE, "jan"));
        assertNotEquals(new AddIntervieweeCommand(VALID_INTERVIEWEE_JANE, "jan"),
                new AddIntervieweeCommand(VALID_INTERVIEWEE_JANE));
    }
}
