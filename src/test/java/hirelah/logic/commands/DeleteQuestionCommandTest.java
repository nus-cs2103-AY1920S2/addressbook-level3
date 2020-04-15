package hirelah.logic.commands;

import static hirelah.logic.commands.CommandTestUtil.assertCommandSuccess;
import static hirelah.logic.commands.DeleteQuestionCommand.MESSAGE_DELETE_QUESTION_SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import hirelah.commons.exceptions.IllegalValueException;
import hirelah.model.Model;
import hirelah.model.ModelManager;
import hirelah.model.hirelah.QuestionList;

class DeleteQuestionCommandTest {

    @Test
    void execute_deleteQuestion_success() throws IllegalValueException {
        StorageStub storage = new StorageStub();
        Model actual = new ModelManager();
        QuestionList actualQuestionList = new QuestionList();
        actualQuestionList.add("What is this");
        actual.setQuestionList(actualQuestionList);

        QuestionList expectedQuestionList = new QuestionList();

        Model expected = new ModelManager();
        expected.setQuestionList(expectedQuestionList);
        DeleteQuestionCommand command = new DeleteQuestionCommand(1);
        CommandResult expectedCommandResult =
                new ToggleCommandResult(String.format(MESSAGE_DELETE_QUESTION_SUCCESS,
                        "What is this"),
                        ToggleView.QUESTION);

        assertCommandSuccess(command, actual, expectedCommandResult, expected, storage);
    }

    @Test
    void test_equals_success() {
        DeleteQuestionCommand command = new DeleteQuestionCommand(1);
        assertEquals(new DeleteQuestionCommand(1), command);
    }
}
