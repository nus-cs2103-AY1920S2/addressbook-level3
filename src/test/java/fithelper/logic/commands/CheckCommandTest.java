package fithelper.logic.commands;

import static fithelper.logic.commands.CommandTestUtil.assertCommandSuccess;
import static fithelper.testutil.AssertUtil.assertThrows;

import org.junit.jupiter.api.Test;

import fithelper.model.Model;
import fithelper.model.ModelStub;
import fithelper.model.entry.Type;

public class CheckCommandTest {

    @Test
    public void createCommand_withoutKeywords_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CheckCommand(new Type("food"), null));
    }

    @Test
    public void createCommand_withoutCheckType_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CheckCommand(null, "apple"));
    }

    @Test
    public void executeCommand_noMatchingResults_feedbackFailureToUser() {
        // we use a stub to simulate the scenario where the set returned from search is empty
        Model model = new ModelStub();
        CheckCommand command = new CheckCommand(new Type("food"), "test");
        String expectedMessage = CheckCommand.MESSAGE_FAILURE_PART1 + command.getCheckType().getValue()
                + CheckCommand.MESSAGE_FAILURE_PART2 + command.getKeywords() + "\n";
        assertCommandSuccess(command, model, expectedMessage, model);
    }

    @Test
    public void executeCommand_hasMatchingResult_showResultsToUser() {
        // we use a stub to simulate the scenario where the set returned from search is empty
        Model model = new ModelStub();
        CheckCommand command = new CheckCommand(new Type("food"), "test");
        String expectedMessage = CheckCommand.MESSAGE_FAILURE_PART1 + command.getCheckType().getValue()
                + CheckCommand.MESSAGE_FAILURE_PART2 + command.getKeywords() + "\n";
        assertCommandSuccess(command, model, expectedMessage, model);
    }
}
