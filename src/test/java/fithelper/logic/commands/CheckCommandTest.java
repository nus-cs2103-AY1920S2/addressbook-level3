package fithelper.logic.commands;

import static fithelper.logic.commands.CommandTestUtil.assertCommandSuccess;
import static fithelper.testutil.AssertUtil.assertThrows;

import org.junit.jupiter.api.Test;

import fithelper.model.Model;
import fithelper.model.ModelManager;
import fithelper.model.entry.Type;

public class CheckCommandTest {

    @Test
    public void createCommandWithoutKeywordsThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CheckCommand(new Type("food"), null));
    }

    @Test
    public void createCommandWithoutCheckTypeThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CheckCommand(null, "apple"));
    }

    @Test
    public void executeCommandWithKeywordsNotFoundInDatabaseFeedbackFailureToUser() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        CheckCommand command = new CheckCommand(new Type("food"), "ashkdhaiwocviabawfawfa");
        String expectedMessage = CheckCommand.MESSAGE_FAILURE_PART1 + command.getCheckType().getValue()
                + CheckCommand.MESSAGE_FAILURE_PART2 + command.getKeywords() + "\n";
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }
}
