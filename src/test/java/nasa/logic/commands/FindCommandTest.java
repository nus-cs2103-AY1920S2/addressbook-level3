package nasa.logic.commands;

import static nasa.commons.core.Messages.MESSAGE_ACTIVITY_LISTED_OVERVIEW;
import static nasa.logic.commands.CommandTestUtil.assertCommandSuccess;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import nasa.logic.commands.exceptions.CommandException;
import nasa.model.HistoryBook;
import nasa.model.Model;
import nasa.model.ModelManager;
import nasa.model.UserPrefs;
import nasa.model.activity.ActivityContainsKeyWordsPredicate;
import nasa.testutil.NasaBookBuilder;

public class FindCommandTest {

    private Model model = new ModelManager(new NasaBookBuilder().build(), new HistoryBook<>(), new HistoryBook<>(),
            new UserPrefs());
    private Model expectedModel = new ModelManager(new NasaBookBuilder().build(), new HistoryBook<>(),
            new HistoryBook<>(), new UserPrefs());

    @Test
    public void equals() {
        ActivityContainsKeyWordsPredicate firstPredicate =
            new ActivityContainsKeyWordsPredicate(Collections.singletonList("first"));

        ActivityContainsKeyWordsPredicate secondPredicate =
            new ActivityContainsKeyWordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noActivityFound() {
        String expectedMessage = String.format(MESSAGE_ACTIVITY_LISTED_OVERVIEW, 0);
        ActivityContainsKeyWordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredActivityList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multipleKeywords_multipleActivitiesFound() {
        expectedModel = new ModelManager(new NasaBookBuilder().build(), new HistoryBook<>(), new HistoryBook<>(),
                new UserPrefs());

        String expectedMessage = String.format(MESSAGE_ACTIVITY_LISTED_OVERVIEW, 4);
        ActivityContainsKeyWordsPredicate predicate = preparePredicate("Lab");
        FindCommand findCommand = new FindCommand(predicate);
        expectedModel.updateFilteredActivityList(predicate);
        assertTrue(model.equals(expectedModel));
        try {
            CommandResult res = findCommand.execute(model);
            assertEquals(res, new CommandResult(expectedMessage));
        } catch (CommandException e) {
            throw new AssertionError("Execution of command should not fail.", e);
        }
        assertCommandSuccess(findCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Parses {@code userInput} into a {@code ActivityContainsKeywordsPredicate}.
     */
    private ActivityContainsKeyWordsPredicate preparePredicate(String userInput) {
        return new ActivityContainsKeyWordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
