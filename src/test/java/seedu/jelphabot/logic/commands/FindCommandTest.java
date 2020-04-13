package seedu.jelphabot.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jelphabot.commons.core.Messages.MESSAGE_TASKS_LISTED_OVERVIEW;
import static seedu.jelphabot.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jelphabot.testutil.TypicalTasks.CLASS;
import static seedu.jelphabot.testutil.TypicalTasks.ERRAND;
import static seedu.jelphabot.testutil.TypicalTasks.FINALS;
import static seedu.jelphabot.testutil.TypicalTasks.getTypicalJelphaBot;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.jelphabot.model.Model;
import seedu.jelphabot.model.ModelManager;
import seedu.jelphabot.model.UserPrefs;
import seedu.jelphabot.model.task.predicates.DescriptionContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for
 * {@code FindCommand}.
 */
public class FindCommandTest {

    private Model model = new ModelManager(getTypicalJelphaBot(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalJelphaBot(), new UserPrefs());

    @Test
    public void equals() {
        DescriptionContainsKeywordsPredicate firstPredicate = new DescriptionContainsKeywordsPredicate(
                Collections.singletonList("first"));
        DescriptionContainsKeywordsPredicate secondPredicate = new DescriptionContainsKeywordsPredicate(
                Collections.singletonList("second"));

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

        // different commands -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noTaskFound() {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 0);
        DescriptionContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredTaskList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredTaskList());
    }

    @Test
    public void execute_multipleKeywords_multipleTasksFound() {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 3);
        DescriptionContainsKeywordsPredicate predicate = preparePredicate("Yoga Milk Open");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredTaskList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CLASS, ERRAND, FINALS), model.getFilteredTaskList());
    }

    /**
     * Parses {@code userInput} into a {@code DescriptionContainsKeywordsPredicate}.
     */
    private DescriptionContainsKeywordsPredicate preparePredicate(String userInput) {
        return new DescriptionContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
