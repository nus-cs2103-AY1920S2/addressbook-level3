package seedu.recipe.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.recipe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recipe.commons.core.Messages.MESSAGE_RECIPES_LISTED_OVERVIEW;
import static seedu.recipe.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recipe.testutil.Assert.assertThrows;
import static seedu.recipe.testutil.TypicalRecipes.BOILED_CHICKEN;
import static seedu.recipe.testutil.TypicalRecipes.OMELET;
import static seedu.recipe.testutil.TypicalRecipes.STEAMED_EGG;
import static seedu.recipe.testutil.TypicalRecipes.TUNA_BREAD;
import static seedu.recipe.testutil.TypicalRecipes.getTypicalRecipeBook;
import static seedu.recipe.testutil.TypicalRecords.getTypicalRecordBook;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.recipe.logic.commands.recipe.FindCommand;
import seedu.recipe.logic.parser.exceptions.ParseException;
import seedu.recipe.model.Model;
import seedu.recipe.model.ModelManager;
import seedu.recipe.model.UserPrefs;
import seedu.recipe.model.achievement.QuoteBook;
import seedu.recipe.model.plan.PlannedBook;
import seedu.recipe.model.recipe.NameContainsKeywordsPredicate;
import seedu.recipe.ui.tab.Tab;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalRecipeBook(), new UserPrefs(),
            getTypicalRecordBook(), new PlannedBook(), new QuoteBook());
    private Model expectedModel = new ModelManager(getTypicalRecipeBook(), new UserPrefs(),
            getTypicalRecordBook(), new PlannedBook(), new QuoteBook());

    @Test
    public void equals() {
        // Base predicate for comparison
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(true, "first");
        // Same isStrict, different keywords
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(true, "second");
        // Different isStrict, same keywords
        NameContainsKeywordsPredicate thirdPredicate =
                new NameContainsKeywordsPredicate(false, "first");

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);
        FindCommand findThirdCommand = new FindCommand(thirdPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // same isStrict, different keywords -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));

        // different isStrict, same keywords -> returns false
        assertFalse(findFirstCommand.equals(findThirdCommand));
    }

    @Test
    public void execute_zeroKeywords_throwsParseException() {
        assertThrows(ParseException.class, () -> preparePredicate(" "));
    }

    @Test
    public void execute_multipleKeywordsStrict_multipleRecipesFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_RECIPES_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate predicate = preparePredicate("/strict Boiled Omelet Egg");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredRecipeList(predicate);
        CommandResult expectedCommandResult =
                new CommandResult(expectedMessage, false, false, Tab.RECIPES, false);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(Arrays.asList(BOILED_CHICKEN, OMELET, STEAMED_EGG), model.getFilteredRecipeList());
    }

    @Test
    public void execute_multipleKeywordsNonStrict_multipleRecipesFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_RECIPES_LISTED_OVERVIEW, 2);
        NameContainsKeywordsPredicate predicate = preparePredicate("ea");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredRecipeList(predicate);
        CommandResult expectedCommandResult =
                new CommandResult(expectedMessage, false, false, Tab.RECIPES, false);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(Arrays.asList(STEAMED_EGG, TUNA_BREAD), model.getFilteredRecipeList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) throws ParseException {
        String trimmedArgs = userInput.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+", 2);
        if (nameKeywords[0].toLowerCase().equals("/strict")) {
            if (nameKeywords.length < 2) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            return new NameContainsKeywordsPredicate(true,
                    nameKeywords[1].replaceAll("\\s{2,}", " "));
        } else {
            return new NameContainsKeywordsPredicate(false,
                    trimmedArgs.replaceAll("\\s{2,}", " "));
        }
    }
}
